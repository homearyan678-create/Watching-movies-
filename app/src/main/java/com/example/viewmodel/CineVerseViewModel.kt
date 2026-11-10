package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MovieCatalog
import com.example.data.NotificationHelper
import com.example.model.DownloadItem
import com.example.model.DownloadStatus
import com.example.model.Episode
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.model.ReleaseNotification
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
    HOME,
    EXPLORE,
    DOWNLOADS,
    NOTIFICATIONS,
    DETAILS,
    PLAYER
}

class CineVerseViewModel(application: Application) : AndroidViewModel(application) {

    // Navigation
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<AppScreen>()

    // Media details & selection
    private val _selectedMedia = MutableStateFlow<MediaItem?>(null)
    val selectedMedia: StateFlow<MediaItem?> = _selectedMedia.asStateFlow()

    // Master Catalog (includes newly released items updated in real time)
    private val _catalog = MutableStateFlow<List<MediaItem>>(MovieCatalog.worldwideCatalog)
    val catalog: StateFlow<List<MediaItem>> = _catalog.asStateFlow()

    // 18+ Mature Content unlock status (defaults to false for safety, user can unlock via Age Gate)
    private val _is18PlusUnlocked = MutableStateFlow(false)
    val is18PlusUnlocked: StateFlow<Boolean> = _is18PlusUnlocked.asStateFlow()

    private val _showAgeDialog = MutableStateFlow(false)
    val showAgeDialog: StateFlow<Boolean> = _showAgeDialog.asStateFlow()

    private var pending18Media: MediaItem? = null

    // Category Filter for Home/Explore
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Offline Mode Only
    private val _isOfflineOnly = MutableStateFlow(false)
    val isOfflineOnly: StateFlow<Boolean> = _isOfflineOnly.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Watchlist
    private val _watchlist = MutableStateFlow<Set<String>>(setOf("dune-prophecy", "jawan-extended"))
    val watchlist: StateFlow<Set<String>> = _watchlist.asStateFlow()

    // High-Speed Downloads
    private val _downloads = MutableStateFlow<List<DownloadItem>>(
        listOf(
            DownloadItem(
                id = "dune-prophecy-e1",
                mediaId = "dune-prophecy",
                title = "Dune: Prophecy",
                subtitle = "S1:E1 • The Hidden Hand",
                quality = "1080p FHD",
                totalSizeMb = 540.0,
                downloadedMb = 540.0,
                speedMbPerSec = 0.0,
                status = DownloadStatus.COMPLETED,
                is18Plus = false,
                durationMinutes = 62,
                gradientStart = 0xFF8C531B,
                gradientEnd = 0xFF2B1607
            ),
            DownloadItem(
                id = "oppenheimer-offline",
                mediaId = "oppenheimer-imax",
                title = "Oppenheimer: 4K Director's Cut",
                subtitle = "Full Movie • 4K Ultra HD",
                quality = "4K UHD",
                totalSizeMb = 3400.0,
                downloadedMb = 3400.0,
                speedMbPerSec = 0.0,
                status = DownloadStatus.COMPLETED,
                is18Plus = false,
                durationMinutes = 180,
                gradientStart = 0xFFB33912,
                gradientEnd = 0xFF1C0A04
            )
        )
    )
    val downloads: StateFlow<List<DownloadItem>> = _downloads.asStateFlow()

    // Notifications & Release feed
    private val _notifications = MutableStateFlow<List<ReleaseNotification>>(
        listOf(
            ReleaseNotification(
                id = "notif-1",
                mediaId = "dune-prophecy",
                title = "Worldwide Premiere: Dune: Prophecy",
                message = "The prequel saga is now streaming worldwide in 4K Dolby Vision & Atmos!",
                timeAgo = "10 mins ago",
                isRead = false,
                badgeLabel = "HOT PREMIERE"
            ),
            ReleaseNotification(
                id = "notif-2",
                mediaId = "squid-game-s2",
                title = "New Season Alert: Squid Game 2",
                message = "The deadly games return! All episodes available with multi-audio dubs.",
                timeAgo = "1 hour ago",
                isRead = false,
                badgeLabel = "SERIES DROP"
            )
        )
    )
    val notifications: StateFlow<List<ReleaseNotification>> = _notifications.asStateFlow()

    // Floating Push Alert Banner (In-app simulation)
    private val _activeBanner = MutableStateFlow<ReleaseNotification?>(null)
    val activeBanner: StateFlow<ReleaseNotification?> = _activeBanner.asStateFlow()

    // Video Player
    private val _playerMedia = MutableStateFlow<MediaItem?>(null)
    val playerMedia: StateFlow<MediaItem?> = _playerMedia.asStateFlow()

    private val _playerEpisode = MutableStateFlow<Episode?>(null)
    val playerEpisode: StateFlow<Episode?> = _playerEpisode.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playerCurrentSec = MutableStateFlow(45)
    val playerCurrentSec: StateFlow<Int> = _playerCurrentSec.asStateFlow()

    private val _playerTotalSec = MutableStateFlow(7200)
    val playerTotalSec: StateFlow<Int> = _playerTotalSec.asStateFlow()

    private val _playerSpeed = MutableStateFlow(1.0f)
    val playerSpeed: StateFlow<Float> = _playerSpeed.asStateFlow()

    private val _playerAudio = MutableStateFlow("English [Original 5.1]")
    val playerAudio: StateFlow<String> = _playerAudio.asStateFlow()

    private val _playerSubtitle = MutableStateFlow("English [CC]")
    val playerSubtitle: StateFlow<String> = _playerSubtitle.asStateFlow()

    private val _playerQuality = MutableStateFlow("1080p FHD")
    val playerQuality: StateFlow<String> = _playerQuality.asStateFlow()

    private val _isPlayerLocked = MutableStateFlow(false)
    val isPlayerLocked: StateFlow<Boolean> = _isPlayerLocked.asStateFlow()

    private val _isPlayerOffline = MutableStateFlow(false)
    val isPlayerOffline: StateFlow<Boolean> = _isPlayerOffline.asStateFlow()

    // Background jobs
    private var downloadSimulationJob: Job? = null
    private var playerTimerJob: Job? = null
    private var autoReleaseJob: Job? = null
    private var upcomingIndex = 0

    init {
        NotificationHelper.initNotificationChannel(application)
        startDownloadEngine()
        startPlayerTimer()
        startAutoReleaseScheduler()
    }

    // Navigation Methods
    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun handleBack(): Boolean {
        if (_currentScreen.value == AppScreen.PLAYER) {
            closePlayer()
            return true
        }
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
            return true
        }
        if (_currentScreen.value != AppScreen.HOME) {
            _currentScreen.value = AppScreen.HOME
            return true
        }
        return false
    }

    fun openDetails(media: MediaItem) {
        if (media.is18Plus && !_is18PlusUnlocked.value) {
            pending18Media = media
            _showAgeDialog.value = true
        } else {
            _selectedMedia.value = media
            navigateTo(AppScreen.DETAILS)
        }
    }

    // 18+ Verification & Age Gate
    fun request18PlusUnlock(targetMedia: MediaItem? = null) {
        pending18Media = targetMedia
        _showAgeDialog.value = true
    }

    fun dismissAgeDialog() {
        _showAgeDialog.value = false
        pending18Media = null
    }

    fun unlock18PlusWithPin(pin: String): Boolean {
        // Any 4 digit pin or 1818 unlocks the mature catalog
        if (pin.length >= 4) {
            _is18PlusUnlocked.value = true
            _showAgeDialog.value = false
            pending18Media?.let {
                _selectedMedia.value = it
                navigateTo(AppScreen.DETAILS)
                pending18Media = null
            }
            return true
        }
        return false
    }

    fun toggle18PlusMode() {
        if (_is18PlusUnlocked.value) {
            _is18PlusUnlocked.value = false
        } else {
            request18PlusUnlock()
        }
    }

    // Filter & Search
    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleOfflineOnly() {
        _isOfflineOnly.value = !_isOfflineOnly.value
    }

    fun toggleWatchlist(mediaId: String) {
        val current = _watchlist.value.toMutableSet()
        if (current.contains(mediaId)) {
            current.remove(mediaId)
        } else {
            current.add(mediaId)
        }
        _watchlist.value = current
    }

    // High-Speed Download Engine
    fun startDownload(media: MediaItem, episode: Episode? = null, quality: String = "1080p FHD") {
        val downloadId = if (episode != null) "${media.id}-${episode.id}" else media.id
        val existing = _downloads.value.find { it.id == downloadId }
        if (existing != null) {
            // Already in downloads, resume if paused
            if (existing.status == DownloadStatus.PAUSED) {
                resumeDownload(downloadId)
            }
            navigateTo(AppScreen.DOWNLOADS)
            return
        }

        val totalSizeMb = when (quality) {
            "4K UHD" -> if (episode != null) 1200.0 else 3200.0
            "1080p FHD" -> if (episode != null) 540.0 else 1400.0
            "720p HD" -> if (episode != null) 280.0 else 680.0
            else -> if (episode != null) 150.0 else 350.0
        }

        val title = media.title
        val subtitle = if (episode != null) "S${media.seasonCount}:E${episode.episodeNumber} • ${episode.title}" else "Full Movie • $quality"

        val newItem = DownloadItem(
            id = downloadId,
            mediaId = media.id,
            title = title,
            subtitle = subtitle,
            quality = quality,
            totalSizeMb = totalSizeMb,
            downloadedMb = 0.0,
            speedMbPerSec = Random.nextDouble(18.0, 26.5),
            status = DownloadStatus.DOWNLOADING,
            is18Plus = media.is18Plus,
            durationMinutes = episode?.durationMinutes ?: media.durationMinutes,
            gradientStart = media.backdropColorStart,
            gradientEnd = media.backdropColorEnd
        )

        _downloads.value = listOf(newItem) + _downloads.value
        navigateTo(AppScreen.DOWNLOADS)
    }

    fun pauseDownload(id: String) {
        _downloads.value = _downloads.value.map {
            if (it.id == id && it.status == DownloadStatus.DOWNLOADING) {
                it.copy(status = DownloadStatus.PAUSED, speedMbPerSec = 0.0)
            } else it
        }
    }

    fun resumeDownload(id: String) {
        _downloads.value = _downloads.value.map {
            if (it.id == id && it.status == DownloadStatus.PAUSED) {
                it.copy(status = DownloadStatus.DOWNLOADING, speedMbPerSec = Random.nextDouble(18.0, 26.5))
            } else it
        }
    }

    fun cancelOrDeleteDownload(id: String) {
        _downloads.value = _downloads.value.filter { it.id != id }
    }

    private fun startDownloadEngine() {
        downloadSimulationJob?.cancel()
        downloadSimulationJob = viewModelScope.launch {
            while (isActive) {
                delay(750) // High frequency smooth update
                val currentList = _downloads.value
                val hasDownloading = currentList.any { it.status == DownloadStatus.DOWNLOADING }
                if (hasDownloading) {
                    val updated = currentList.map { item ->
                        if (item.status == DownloadStatus.DOWNLOADING) {
                            val speed = Random.nextDouble(17.5, 27.5)
                            val addedMb = speed * 0.75
                            val newDownloaded = (item.downloadedMb + addedMb).coerceAtMost(item.totalSizeMb)
                            if (newDownloaded >= item.totalSizeMb) {
                                // Download completed!
                                NotificationHelper.sendReleaseNotification(
                                    getApplication(),
                                    item.id.hashCode(),
                                    "Download Ready (Offline ⚡)",
                                    "${item.title} has finished downloading in high-speed ${item.quality}. Ready to watch offline!",
                                    item.mediaId
                                )
                                item.copy(
                                    downloadedMb = item.totalSizeMb,
                                    status = DownloadStatus.COMPLETED,
                                    speedMbPerSec = 0.0
                                )
                            } else {
                                item.copy(downloadedMb = newDownloaded, speedMbPerSec = speed)
                            }
                        } else item
                    }
                    _downloads.value = updated
                }
            }
        }
    }

    // Auto-Update Engine: Simulates new release drops with push notifications
    fun checkForNewReleases(manual: Boolean = true) {
        viewModelScope.launch {
            if (upcomingIndex < MovieCatalog.upcomingDrops.size) {
                val newDrop = MovieCatalog.upcomingDrops[upcomingIndex]
                upcomingIndex++

                // Add to catalog if not exists
                if (_catalog.value.none { it.id == newDrop.id }) {
                    _catalog.value = listOf(newDrop) + _catalog.value

                    val notif = ReleaseNotification(
                        id = "drop-${newDrop.id}-${System.currentTimeMillis()}",
                        mediaId = newDrop.id,
                        title = "🔥 Worldwide Release: ${newDrop.title}",
                        message = "Just released worldwide! Watch in 4K UHD or download for high-speed offline viewing.",
                        timeAgo = "Just Now",
                        is18Plus = newDrop.is18Plus,
                        badgeLabel = if (newDrop.is18Plus) "18+ EXCLUSIVE" else "WORLD PREMIERE"
                    )

                    _notifications.value = listOf(notif) + _notifications.value

                    // Trigger Android native push notification
                    NotificationHelper.sendReleaseNotification(
                        getApplication(),
                        newDrop.id.hashCode(),
                        notif.title,
                        notif.message,
                        newDrop.id
                    )

                    // Show top floating banner in app
                    _activeBanner.value = notif
                    delay(6000)
                    if (_activeBanner.value?.id == notif.id) {
                        _activeBanner.value = null
                    }
                }
            } else if (manual) {
                // If all upcoming already dropped, show latest notice
                val sample = _catalog.value.firstOrNull() ?: return@launch
                val notif = ReleaseNotification(
                    id = "refresh-${System.currentTimeMillis()}",
                    mediaId = sample.id,
                    title = "✨ Catalog Up-to-Date",
                    message = "All the latest worldwide movies and web series are synced and ready to stream or download.",
                    timeAgo = "Just Now",
                    badgeLabel = "SYNCED"
                )
                _activeBanner.value = notif
                delay(4000)
                if (_activeBanner.value?.id == notif.id) {
                    _activeBanner.value = null
                }
            }
        }
    }

    fun dismissBanner() {
        _activeBanner.value = null
    }

    private fun startAutoReleaseScheduler() {
        autoReleaseJob?.cancel()
        autoReleaseJob = viewModelScope.launch {
            // First drop happens after 20 seconds, subsequent drops every 60 seconds
            delay(20000)
            while (isActive) {
                if (upcomingIndex < MovieCatalog.upcomingDrops.size) {
                    checkForNewReleases(manual = false)
                }
                delay(60000)
            }
        }
    }

    // Video Player Controls
    fun playMedia(media: MediaItem, episode: Episode? = null, isOffline: Boolean = false) {
        if (media.is18Plus && !_is18PlusUnlocked.value) {
            request18PlusUnlock(media)
            return
        }

        _playerMedia.value = media
        _playerEpisode.value = episode
        _isPlayerOffline.value = isOffline
        _isPlaying.value = true
        _playerCurrentSec.value = 10
        val totalSec = ((episode?.durationMinutes ?: media.durationMinutes) * 60)
        _playerTotalSec.value = totalSec
        _isPlayerLocked.value = false
        navigateTo(AppScreen.PLAYER)
    }

    fun playOfflineDownload(downloadItem: DownloadItem) {
        val media = _catalog.value.find { it.id == downloadItem.mediaId } ?: MediaItem(
            id = downloadItem.mediaId,
            title = downloadItem.title,
            type = MediaType.MOVIE,
            overview = "Offline downloaded title ready for instant high-speed playback.",
            releaseYear = 2026,
            releaseDateString = "Offline",
            rating = 8.8,
            contentRating = if (downloadItem.is18Plus) "18+ Adult" else "PG-13",
            is18Plus = downloadItem.is18Plus,
            genres = listOf("Offline", "Action"),
            country = "Global",
            language = "Original",
            director = "Downloaded",
            cast = emptyList(),
            backdropColorStart = downloadItem.gradientStart,
            backdropColorEnd = downloadItem.gradientEnd
        )

        val episode = media.seasons.flatMap { it.episodes }.find { it.id == downloadItem.id.substringAfterLast("-") }
        playMedia(media = media, episode = episode, isOffline = true)
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun seekTo(seconds: Int) {
        _playerCurrentSec.value = seconds.coerceIn(0, _playerTotalSec.value)
    }

    fun skipForward10() {
        seekTo(_playerCurrentSec.value + 10)
    }

    fun skipBackward10() {
        seekTo(_playerCurrentSec.value - 10)
    }

    fun setPlayerSpeed(speed: Float) {
        _playerSpeed.value = speed
    }

    fun setPlayerAudio(audio: String) {
        _playerAudio.value = audio
    }

    fun setPlayerSubtitle(sub: String) {
        _playerSubtitle.value = sub
    }

    fun setPlayerQuality(quality: String) {
        _playerQuality.value = quality
    }

    fun togglePlayerLock() {
        _isPlayerLocked.value = !_isPlayerLocked.value
    }

    fun closePlayer() {
        _isPlaying.value = false
        _playerMedia.value = null
        _playerEpisode.value = null
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
        } else {
            _currentScreen.value = AppScreen.HOME
        }
    }

    private fun startPlayerTimer() {
        playerTimerJob?.cancel()
        playerTimerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                if (_isPlaying.value && _currentScreen.value == AppScreen.PLAYER) {
                    val nextSec = _playerCurrentSec.value + 1
                    if (nextSec >= _playerTotalSec.value) {
                        _isPlaying.value = false
                    } else {
                        _playerCurrentSec.value = nextSec
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        downloadSimulationJob?.cancel()
        playerTimerJob?.cancel()
        autoReleaseJob?.cancel()
    }
}
