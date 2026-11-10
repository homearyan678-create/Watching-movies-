package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CinematicBackground
import com.example.ui.theme.CinematicBluePrimary
import com.example.ui.theme.CinematicCardBorder
import com.example.ui.theme.CinematicGreen
import com.example.ui.theme.CinematicRedMature
import com.example.ui.theme.CinematicTextPrimary
import com.example.ui.theme.CinematicTextSecondary

@Composable
fun TopBar(
    is18PlusUnlocked: Boolean,
    isOfflineOnly: Boolean,
    unreadNotificationCount: Int,
    on18PlusToggle: () -> Unit,
    onOfflineToggle: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CinematicBackground.copy(alpha = 0.95f))
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // App Branding Logo & Name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.testTag("app_brand_logo")
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CinematicBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CV",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Cine",
                            color = CinematicTextPrimary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Verse",
                            color = CinematicBluePrimary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Text(
                        text = "Worldwide & Offline",
                        color = CinematicTextSecondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Quick Actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Offline Mode Pill Toggle
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isOfflineOnly) CinematicGreen.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.08f))
                        .border(1.dp, if (isOfflineOnly) CinematicGreen else CinematicCardBorder, RoundedCornerShape(16.dp))
                        .clickable { onOfflineToggle() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("offline_mode_toggle"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.OfflinePin,
                            contentDescription = "Offline Mode",
                            tint = if (isOfflineOnly) CinematicGreen else CinematicTextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isOfflineOnly) "OFFLINE" else "ONLINE",
                            color = if (isOfflineOnly) CinematicGreen else CinematicTextSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 18+ Mature Mode Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (is18PlusUnlocked) CinematicRedMature else Color.White.copy(alpha = 0.08f))
                        .border(1.dp, if (is18PlusUnlocked) CinematicRedMature else CinematicCardBorder, RoundedCornerShape(16.dp))
                        .clickable { on18PlusToggle() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("18_plus_mode_toggle"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (is18PlusUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                            contentDescription = "18+ Status",
                            tint = if (is18PlusUnlocked) Color.White else CinematicRedMature,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "18+",
                            color = if (is18PlusUnlocked) Color.White else CinematicRedMature,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Search Icon
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Worldwide Movies & Series",
                        tint = CinematicTextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Notification Bell with Badge
                IconButton(
                    onClick = onNotificationsClick,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("notifications_bell")
                ) {
                    BadgedBox(
                        badge = {
                            if (unreadNotificationCount > 0) {
                                Badge(
                                    containerColor = CinematicRedMature,
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = unreadNotificationCount.toString(),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Release Notifications",
                            tint = CinematicTextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
