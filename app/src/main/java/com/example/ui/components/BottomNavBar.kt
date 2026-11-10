package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CinematicBackground
import com.example.ui.theme.CinematicBluePrimary
import com.example.ui.theme.CinematicRedMature
import com.example.ui.theme.CinematicSurface
import com.example.ui.theme.CinematicTextPrimary
import com.example.ui.theme.CinematicTextSecondary
import com.example.viewmodel.AppScreen

data class NavItem(
    val screen: AppScreen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun BottomNavBar(
    currentScreen: AppScreen,
    downloadCount: Int,
    notificationCount: Int,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(AppScreen.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        NavItem(AppScreen.EXPLORE, "Explore", Icons.Filled.Search, Icons.Outlined.Search, "nav_explore"),
        NavItem(AppScreen.DOWNLOADS, "Downloads", Icons.Filled.Download, Icons.Outlined.Download, "nav_downloads"),
        NavItem(AppScreen.NOTIFICATIONS, "New Releases", Icons.Filled.Notifications, Icons.Outlined.Notifications, "nav_notifications")
    )

    NavigationBar(
        modifier = modifier.testTag("bottom_nav_bar"),
        containerColor = CinematicSurface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    if (item.screen == AppScreen.DOWNLOADS && downloadCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = CinematicBluePrimary,
                                    contentColor = Color.White
                                ) {
                                    Text(text = downloadCount.toString(), fontSize = 9.sp)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else if (item.screen == AppScreen.NOTIFICATIONS && notificationCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = CinematicRedMature,
                                    contentColor = Color.White
                                ) {
                                    Text(text = notificationCount.toString(), fontSize = 9.sp)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CinematicTextPrimary,
                    selectedTextColor = CinematicBluePrimary,
                    unselectedIconColor = CinematicTextSecondary,
                    unselectedTextColor = CinematicTextSecondary,
                    indicatorColor = CinematicBluePrimary.copy(alpha = 0.25f)
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}
