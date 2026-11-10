package com.example.model

data class ReleaseNotification(
    val id: String,
    val mediaId: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val is18Plus: Boolean = false,
    val badgeLabel: String = "NEW RELEASE"
)
