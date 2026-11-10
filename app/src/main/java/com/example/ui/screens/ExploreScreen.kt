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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MediaItem
import com.example.model.MediaType
import com.example.ui.components.MediaCard
import com.example.ui.theme.CinematicBackground
import com.example.ui.theme.CinematicBluePrimary
import com.example.ui.theme.CinematicCardBorder
import com.example.ui.theme.CinematicRedMature
import com.example.ui.theme.CinematicSurface
import com.example.ui.theme.CinematicTextMuted
import com.example.ui.theme.CinematicTextPrimary
import com.example.ui.theme.CinematicTextSecondary

@Composable
fun ExploreScreen(
    catalog: List<MediaItem>,
    is18PlusUnlocked: Boolean,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onMediaClick: (MediaItem) -> Unit,
    onDownloadClick: (MediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRegion by remember { mutableStateOf("All Regions") }
    var selectedMediaType by remember { mutableStateOf("All") }

    val regions = listOf("All Regions", "Hollywood", "Bollywood", "Korea", "Japan", "Europe")
    val mediaTypes = listOf("All", "Movies", "Web Series", "18+ Mature")

    val filteredList = catalog.filter { media ->
        val matchesQuery = searchQuery.isBlank() ||
            media.title.contains(searchQuery, ignoreCase = true) ||
            media.director.contains(searchQuery, ignoreCase = true) ||
            media.cast.any { it.contains(searchQuery, ignoreCase = true) } ||
            media.genres.any { it.contains(searchQuery, ignoreCase = true) } ||
            media.country.contains(searchQuery, ignoreCase = true)

        val matchesRegion = when (selectedRegion) {
            "All Regions" -> true
            "Hollywood" -> media.country.contains("Hollywood", ignoreCase = true)
            "Bollywood" -> media.country.contains("Bollywood", ignoreCase = true) || media.language.contains("Hindi", ignoreCase = true)
            "Korea" -> media.country.contains("Korea", ignoreCase = true)
            "Japan" -> media.country.contains("Japan", ignoreCase = true)
            "Europe" -> media.country.contains("Europe", ignoreCase = true) || media.country.contains("Spain", ignoreCase = true) || media.country.contains("UK", ignoreCase = true)
            else -> true
        }

        val matchesType = when (selectedMediaType) {
            "All" -> true
            "Movies" -> media.type == MediaType.MOVIE
            "Web Series" -> media.type == MediaType.SERIES
            "18+ Mature" -> media.is18Plus
            else -> true
        }

        matchesQuery && matchesRegion && matchesType
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CinematicBackground)
            .padding(top = 16.dp)
            .testTag("explore_screen")
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = { Text("Search worldwide movies, series, actors...", color = CinematicTextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = CinematicBluePrimary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            tint = CinematicTextSecondary
                        )
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CinematicSurface,
                unfocusedContainerColor = CinematicSurface,
                focusedBorderColor = CinematicBluePrimary,
                unfocusedBorderColor = CinematicCardBorder,
                focusedTextColor = CinematicTextPrimary,
                unfocusedTextColor = CinematicTextPrimary,
                cursorColor = CinematicBluePrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("search_input_field")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Region Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            regions.forEach { region ->
                val isSelected = selectedRegion == region
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) CinematicBluePrimary else CinematicSurface)
                        .border(1.dp, if (isSelected) CinematicBluePrimary else CinematicCardBorder, RoundedCornerShape(16.dp))
                        .clickable { selectedRegion = region }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = region,
                        color = if (isSelected) Color.White else CinematicTextPrimary,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Type Filter Chips (Movies, Series, 18+ Mature)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            mediaTypes.forEach { type ->
                val isSelected = selectedMediaType == type
                val is18 = type == "18+ Mature"
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) {
                                if (is18) CinematicRedMature else Color.White.copy(alpha = 0.2f)
                            } else {
                                Color.Transparent
                            }
                        )
                        .border(1.dp, if (is18) CinematicRedMature.copy(alpha = 0.6f) else CinematicCardBorder, RoundedCornerShape(8.dp))
                        .clickable { selectedMediaType = type }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = type,
                        color = if (isSelected) Color.White else if (is18) CinematicRedMature else CinematicTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Results Grid
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = CinematicTextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "No Titles Found",
                        color = CinematicTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Try searching for Hollywood, Bollywood, K-Drama, or Action",
                        color = CinematicTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredList, key = { it.id }) { item ->
                    MediaCard(
                        media = item,
                        is18PlusUnlocked = is18PlusUnlocked,
                        onCardClick = { onMediaClick(item) },
                        onDownloadClick = { onDownloadClick(item) },
                        cardWidth = 175,
                        cardHeight = 220
                    )
                }
            }
        }
    }
}
