package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.ui.components.HeroCarousel
import com.example.ui.components.MediaCard
import com.example.ui.theme.CinematicAccentGold
import com.example.ui.theme.CinematicBackground
import com.example.ui.theme.CinematicBluePrimary
import com.example.ui.theme.CinematicCardBorder
import com.example.ui.theme.CinematicGreen
import com.example.ui.theme.CinematicRedMature
import com.example.ui.theme.CinematicSurface
import com.example.ui.theme.CinematicTextPrimary
import com.example.ui.theme.CinematicTextSecondary

@Composable
fun HomeScreen(
    catalog: List<MediaItem>,
    is18PlusUnlocked: Boolean,
    isOfflineOnly: Boolean,
    selectedCategory: String,
    watchlist: Set<String>,
    onSelectCategory: (String) -> Unit,
    onMediaClick: (MediaItem) -> Unit,
    onDownloadClick: (MediaItem) -> Unit,
    onWatchlistToggle: (String) -> Unit,
    onRequest18Unlock: () -> Unit,
    onCheckNewReleases: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        "All",
        "Movies",
        "Web Series",
        "18+ Mature",
        "Hollywood",
        "Bollywood",
        "K-Drama",
        "Anime"
    )

    // Filtered master catalog based on active category
    val filteredList = catalog.filter { item ->
        when (selectedCategory) {
            "All" -> true
            "Movies" -> item.type == MediaType.MOVIE
            "Web Series" -> item.type == MediaType.SERIES
            "18+ Mature" -> item.is18Plus
            "Hollywood" -> item.country.contains("Hollywood", ignoreCase = true)
            "Bollywood" -> item.country.contains("Bollywood", ignoreCase = true)
            "K-Drama" -> item.country.contains("Korea", ignoreCase = true)
            "Anime" -> item.genres.contains("Anime") || item.country.contains("Japan", ignoreCase = true)
            else -> true
        }
    }

    val featuredItem = filteredList.firstOrNull { it.isNewRelease } ?: filteredList.firstOrNull() ?: catalog.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CinematicBackground)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Featured Carousel
        item {
            HeroCarousel(
                featuredMedia = featuredItem,
                isInWatchlist = watchlist.contains(featuredItem.id),
                onWatchClick = { onMediaClick(featuredItem) },
                onDownloadClick = { onDownloadClick(featuredItem) },
                onWatchlistToggle = { onWatchlistToggle(featuredItem.id) },
                onDetailsClick = { onMediaClick(featuredItem) }
            )
        }

        // Category Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    val is18Chip = cat == "18+ Mature"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) {
                                    if (is18Chip) CinematicRedMature else CinematicBluePrimary
                                } else {
                                    CinematicSurface
                                }
                            )
                            .border(
                                1.dp,
                                if (is18Chip) CinematicRedMature.copy(alpha = 0.5f) else CinematicCardBorder,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                if (is18Chip && !is18PlusUnlocked) {
                                    onRequest18Unlock()
                                } else {
                                    onSelectCategory(cat)
                                }
                            }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("category_chip_$cat"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (is18Chip) {
                                Icon(
                                    imageVector = if (is18PlusUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else CinematicRedMature,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else CinematicTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        // Live Auto-Update Trigger Banner
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CinematicSurface)
                    .border(1.dp, CinematicCardBorder, RoundedCornerShape(10.dp))
                    .clickable { onCheckNewReleases() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ElectricBolt,
                        contentDescription = null,
                        tint = CinematicAccentGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Auto-Sync Live Releases Active",
                            color = CinematicTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tap to check for new worldwide movies & series drops",
                            color = CinematicTextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = CinematicTextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Section 1: Just Released Worldwide (Auto-Updated)
        val newReleases = catalog.filter { it.isNewRelease }
        if (newReleases.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Just Released Worldwide",
                    subtitle = "Fresh global cinema & premieres",
                    icon = Icons.Default.NewReleases,
                    iconTint = CinematicBluePrimary
                )
                MediaHorizontalRow(
                    items = newReleases,
                    is18PlusUnlocked = is18PlusUnlocked,
                    onMediaClick = onMediaClick,
                    onDownloadClick = onDownloadClick
                )
            }
        }

        // Section 2: Trending Across The Globe
        val trending = catalog.filter { it.isTrending }
        item {
            SectionHeader(
                title = "Trending Across the Globe",
                subtitle = "Most watched movies & web series this week",
                icon = Icons.Default.Whatshot,
                iconTint = CinematicAccentGold
            )
            MediaHorizontalRow(
                items = trending,
                is18PlusUnlocked = is18PlusUnlocked,
                onMediaClick = onMediaClick,
                onDownloadClick = onDownloadClick
            )
        }

        // Section 3: 18+ Mature & Uncut Thrillers (Directly answering user's request)
        val matureList = catalog.filter { it.is18Plus }
        item {
            SectionHeader(
                title = "18+ Mature & R-Rated Collection",
                subtitle = if (is18PlusUnlocked) "Unlocked • TV-MA, Crime & Dark Thrillers" else "Locked • Tap to verify 18+ age",
                icon = if (is18PlusUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                iconTint = CinematicRedMature,
                actionText = if (!is18PlusUnlocked) "Unlock 18+" else null,
                onActionClick = if (!is18PlusUnlocked) onRequest18Unlock else null
            )
            MediaHorizontalRow(
                items = matureList,
                is18PlusUnlocked = is18PlusUnlocked,
                onMediaClick = onMediaClick,
                onDownloadClick = onDownloadClick
            )
        }

        // Section 4: Hollywood Blockbusters
        val hollywood = catalog.filter { it.country.contains("Hollywood", ignoreCase = true) }
        item {
            SectionHeader(
                title = "Hollywood Blockbusters",
                subtitle = "Award-winning sci-fi, action, & epics",
                icon = Icons.Default.Whatshot,
                iconTint = CinematicBluePrimary
            )
            MediaHorizontalRow(
                items = hollywood,
                is18PlusUnlocked = is18PlusUnlocked,
                onMediaClick = onMediaClick,
                onDownloadClick = onDownloadClick
            )
        }

        // Section 5: Bollywood & South Asian
        val bollywood = catalog.filter { it.country.contains("Bollywood", ignoreCase = true) || it.language.contains("Hindi", ignoreCase = true) }
        item {
            SectionHeader(
                title = "Bollywood & Desi Cinema",
                subtitle = "High-octane action & purvanchal sagas",
                icon = Icons.Default.Whatshot,
                iconTint = CinematicAccentGold
            )
            MediaHorizontalRow(
                items = bollywood,
                is18PlusUnlocked = is18PlusUnlocked,
                onMediaClick = onMediaClick,
                onDownloadClick = onDownloadClick
            )
        }

        // Section 6: Asian Cinema & K-Dramas / Anime
        val asian = catalog.filter { it.country.contains("Korea", ignoreCase = true) || it.country.contains("Japan", ignoreCase = true) }
        item {
            SectionHeader(
                title = "K-Dramas & Asian Cinema",
                subtitle = "Thrilling revenge series & anime hits",
                icon = Icons.Default.Whatshot,
                iconTint = CinematicGreen
            )
            MediaHorizontalRow(
                items = asian,
                is18PlusUnlocked = is18PlusUnlocked,
                onMediaClick = onMediaClick,
                onDownloadClick = onDownloadClick
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    color = CinematicTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = subtitle,
                color = CinematicTextSecondary,
                fontSize = 11.sp
            )
        }

        if (actionText != null && onActionClick != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(CinematicRedMature.copy(alpha = 0.2f))
                    .border(1.dp, CinematicRedMature, RoundedCornerShape(12.dp))
                    .clickable { onActionClick() }
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = actionText,
                    color = CinematicRedMature,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MediaHorizontalRow(
    items: List<MediaItem>,
    is18PlusUnlocked: Boolean,
    onMediaClick: (MediaItem) -> Unit,
    onDownloadClick: (MediaItem) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items, key = { it.id }) { media ->
            MediaCard(
                media = media,
                is18PlusUnlocked = is18PlusUnlocked,
                onCardClick = { onMediaClick(media) },
                onDownloadClick = { onDownloadClick(media) }
            )
        }
    }
}
