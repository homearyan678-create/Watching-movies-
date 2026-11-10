package com.example.model

enum class DownloadStatus {
    QUEUED,
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED
}

data class DownloadItem(
    val id: String,
    val mediaId: String,
    val title: String,
    val subtitle: String, // e.g. "Season 1 - Episode 1" or "Movie - 4K Ultra HD"
    val quality: String,   // "4K UHD", "1080p FHD", "720p HD"
    val totalSizeMb: Double,
    val downloadedMb: Double = 0.0,
    val speedMbPerSec: Double = 18.5,
    val status: DownloadStatus = DownloadStatus.DOWNLOADING,
    val timestamp: Long = System.currentTimeMillis(),
    val is18Plus: Boolean = false,
    val durationMinutes: Int = 120,
    val gradientStart: Long = 0xFF141E30,
    val gradientEnd: Long = 0xFF243B55
) {
    val progress: Float
        get() = if (totalSizeMb > 0) (downloadedMb / totalSizeMb).coerceIn(0.0, 1.0).toFloat() else 0f

    val percentage: Int
        get() = (progress * 100).toInt()

    val formattedRemaining: String
        get() {
            if (status == DownloadStatus.COMPLETED) return "Downloaded • High-Speed Ready"
            if (status == DownloadStatus.PAUSED) return "Paused • ${(downloadedMb).toInt()} / ${(totalSizeMb).toInt()} MB"
            val remainingMb = (totalSizeMb - downloadedMb).coerceAtLeast(0.0)
            val secondsRemaining = if (speedMbPerSec > 0) (remainingMb / speedMbPerSec).toInt() else 0
            return "${(downloadedMb).toInt()} MB / ${(totalSizeMb).toInt()} MB • ${speedMbPerSec.formatSpeed()} • ${secondsRemaining}s left"
        }
}

private fun Double.formatSpeed(): String = String.format("%.1f MB/s", this)
