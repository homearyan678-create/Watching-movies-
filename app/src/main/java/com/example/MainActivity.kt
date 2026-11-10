package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.Episode
import com.example.model.MediaItem
import com.example.ui.components.BottomNavBar
import com.example.ui.components.FloatingNotificationBanner
import com.example.ui.components.TopBar
import com.example.ui.dialogs.AgeVerificationDialog
import com.example.ui.dialogs.DownloadQualityDialog
import com.example.ui.screens.DetailScreen
import com.example.ui.screens.DownloadsScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.PlayerScreen
import com.example.ui.theme.CinematicBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.CineVerseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val initialMediaId = intent?.getStringExtra("EXTRA_MEDIA_ID")

        setContent {
            MyApplicationTheme {
                val viewModel: CineVerseViewModel = viewModel()

                LaunchedEffect(initialMediaId) {
                    if (!initialMediaId.isNullOrEmpty()) {
                        val media = viewModel.catalog.value.find { it.id == initialMediaId }
                        if (media != null) {
                            viewModel.openDetails(media)
                        }
                    }
                }

                CineVerseApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CineVerseApp(viewModel: CineVerseViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val catalog by viewModel.catalog.collectAsStateWithLifecycle()
    val is18PlusUnlocked by viewModel.is18PlusUnlocked.collectAsStateWithLifecycle()
    val showAgeDialog by viewModel.showAgeDialog.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val isOfflineOnly by viewModel.isOfflineOnly.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val watchlist by viewModel.watchlist.collectAsStateWithLifecycle()
    val downloads by viewModel.downloads.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val activeBanner by viewModel.activeBanner.collectAsStateWithLifecycle()
    val selectedMedia by viewModel.selectedMedia.collectAsStateWithLifecycle()

    // Player state
    val playerMedia by viewModel.playerMedia.collectAsStateWithLifecycle()
    val playerEpisode by viewModel.playerEpisode.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val playerCurrentSec by viewModel.playerCurrentSec.collectAsStateWithLifecycle()
    val playerTotalSec by viewModel.playerTotalSec.collectAsStateWithLifecycle()
    val playerSpeed by viewModel.playerSpeed.collectAsStateWithLifecycle()
    val playerAudio by viewModel.playerAudio.collectAsStateWithLifecycle()
    val playerSubtitle by viewModel.playerSubtitle.collectAsStateWithLifecycle()
    val playerQuality by viewModel.playerQuality.collectAsStateWithLifecycle()
    val isPlayerLocked by viewModel.isPlayerLocked.collectAsStateWithLifecycle()
    val isPlayerOffline by viewModel.isPlayerOffline.collectAsStateWithLifecycle()

    // Download Dialog state
    var downloadDialogMedia by remember { mutableStateOf<MediaItem?>(null) }
    var downloadDialogEpisode by remember { mutableStateOf<Episode?>(null) }

    // Intercept back presses
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        viewModel.handleBack()
    }

    val isTopLevelScreen = currentScreen in listOf(
        AppScreen.HOME,
        AppScreen.EXPLORE,
        AppScreen.DOWNLOADS,
        AppScreen.NOTIFICATIONS
    )

    val activeDownloadingCount = downloads.count { it.status == com.example.model.DownloadStatus.DOWNLOADING }
    val unreadNotifsCount = notifications.count { !it.isRead }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CinematicBackground,
        topBar = {
            if (isTopLevelScreen) {
                TopBar(
                    is18PlusUnlocked = is18PlusUnlocked,
                    isOfflineOnly = isOfflineOnly,
                    unreadNotificationCount = unreadNotifsCount,
                    on18PlusToggle = { viewModel.toggle18PlusMode() },
                    onOfflineToggle = { viewModel.toggleOfflineOnly() },
                    onSearchClick = { viewModel.navigateTo(AppScreen.EXPLORE) },
                    onNotificationsClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) }
                )
            }
        },
        bottomBar = {
            if (isTopLevelScreen) {
                BottomNavBar(
                    currentScreen = currentScreen,
                    downloadCount = activeDownloadingCount,
                    notificationCount = unreadNotifsCount,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        catalog = catalog,
                        is18PlusUnlocked = is18PlusUnlocked,
                        isOfflineOnly = isOfflineOnly,
                        selectedCategory = selectedCategory,
                        watchlist = watchlist,
                        onSelectCategory = { viewModel.selectCategory(it) },
                        onMediaClick = { viewModel.openDetails(it) },
                        onDownloadClick = { media ->
                            downloadDialogMedia = media
                            downloadDialogEpisode = null
                        },
                        onWatchlistToggle = { viewModel.toggleWatchlist(it) },
                        onRequest18Unlock = { viewModel.request18PlusUnlock() },
                        onCheckNewReleases = { viewModel.checkForNewReleases(manual = true) }
                    )
                }

                AppScreen.EXPLORE -> {
                    ExploreScreen(
                        catalog = catalog,
                        is18PlusUnlocked = is18PlusUnlocked,
                        searchQuery = searchQuery,
                        onQueryChange = { viewModel.updateSearchQuery(it) },
                        onMediaClick = { viewModel.openDetails(it) },
                        onDownloadClick = { media ->
                            downloadDialogMedia = media
                            downloadDialogEpisode = null
                        }
                    )
                }

                AppScreen.DOWNLOADS -> {
                    DownloadsScreen(
                        downloads = downloads,
                        onPlayOffline = { viewModel.playOfflineDownload(it) },
                        onPauseDownload = { viewModel.pauseDownload(it) },
                        onResumeDownload = { viewModel.resumeDownload(it) },
                        onDeleteDownload = { viewModel.cancelOrDeleteDownload(it) },
                        onBrowseCatalog = { viewModel.navigateTo(AppScreen.HOME) }
                    )
                }

                AppScreen.NOTIFICATIONS -> {
                    NotificationsScreen(
                        notifications = notifications,
                        onNotificationClick = { mediaId ->
                            val media = catalog.find { it.id == mediaId }
                            if (media != null) {
                                viewModel.openDetails(media)
                            }
                        },
                        onCheckNewReleases = { viewModel.checkForNewReleases(manual = true) }
                    )
                }

                AppScreen.DETAILS -> {
                    selectedMedia?.let { media ->
                        DetailScreen(
                            media = media,
                            isInWatchlist = watchlist.contains(media.id),
                            onBack = { viewModel.handleBack() },
                            onPlayClick = { ep ->
                                viewModel.playMedia(media = media, episode = ep, isOffline = false)
                            },
                            onDownloadClick = { ep ->
                                downloadDialogMedia = media
                                downloadDialogEpisode = ep
                            },
                            onWatchlistToggle = { viewModel.toggleWatchlist(media.id) }
                        )
                    }
                }

                AppScreen.PLAYER -> {
                    playerMedia?.let { media ->
                        PlayerScreen(
                            media = media,
                            episode = playerEpisode,
                            isPlaying = isPlaying,
                            currentSec = playerCurrentSec,
                            totalSec = playerTotalSec,
                            speed = playerSpeed,
                            audioTrack = playerAudio,
                            subtitle = playerSubtitle,
                            quality = playerQuality,
                            isLocked = isPlayerLocked,
                            isOffline = isPlayerOffline,
                            onBack = { viewModel.closePlayer() },
                            onTogglePlayPause = { viewModel.togglePlayPause() },
                            onSeek = { viewModel.seekTo(it) },
                            onSkipForward = { viewModel.skipForward10() },
                            onSkipBackward = { viewModel.skipBackward10() },
                            onSetSpeed = { viewModel.setPlayerSpeed(it) },
                            onSetAudio = { viewModel.setPlayerAudio(it) },
                            onSetSubtitle = { viewModel.setPlayerSubtitle(it) },
                            onSetQuality = { viewModel.setPlayerQuality(it) },
                            onToggleLock = { viewModel.togglePlayerLock() }
                        )
                    }
                }
            }

            // Top Floating Drop Banner (when new release alerts arrive)
            FloatingNotificationBanner(
                notification = activeBanner,
                onBannerClick = { mediaId ->
                    viewModel.dismissBanner()
                    val media = catalog.find { it.id == mediaId }
                    if (media != null) {
                        viewModel.openDetails(media)
                    }
                },
                onDismiss = { viewModel.dismissBanner() },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // Age Verification Modal (18+ Pin Gate)
            if (showAgeDialog) {
                AgeVerificationDialog(
                    onDismiss = { viewModel.dismissAgeDialog() },
                    onVerifySuccess = { pin ->
                        viewModel.unlock18PlusWithPin(pin)
                    }
                )
            }

            // Download Quality Modal
            downloadDialogMedia?.let { media ->
                DownloadQualityDialog(
                    media = media,
                    episode = downloadDialogEpisode,
                    onDismiss = {
                        downloadDialogMedia = null
                        downloadDialogEpisode = null
                    },
                    onConfirmDownload = { quality ->
                        viewModel.startDownload(
                            media = media,
                            episode = downloadDialogEpisode,
                            quality = quality
                        )
                        downloadDialogMedia = null
                        downloadDialogEpisode = null
                    }
                )
            }
        }
    }
}
