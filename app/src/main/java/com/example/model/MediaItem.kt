package com.example.model

enum class MediaType {
    MOVIE,
    SERIES
}

data class Episode(
    val id: String,
    val episodeNumber: Int,
    val title: String,
    val durationMinutes: Int,
    val overview: String,
    val fileSizeMb: Int,
    val thumbnailGradientStart: Long = 0xFF1E2640,
    val thumbnailGradientEnd: Long = 0xFF0D111A
)

data class Season(
    val seasonNumber: Int,
    val title: String,
    val episodes: List<Episode>
)

data class MediaItem(
    val id: String,
    val title: String,
    val originalTitle: String = "",
    val type: MediaType,
    val overview: String,
    val releaseYear: Int,
    val releaseDateString: String,
    val durationMinutes: Int = 125, // for movies
    val seasonCount: Int = 1,       // for series
    val rating: Double,
    val contentRating: String,      // "PG-13", "R", "TV-MA", "18+ Adult", "PG"
    val is18Plus: Boolean = false,
    val genres: List<String>,
    val country: String,            // "Hollywood (USA)", "Bollywood (India)", "South Korea", "Japan", etc.
    val language: String,           // "English", "Hindi", "Korean", "Japanese", "Dual Audio", etc.
    val director: String,
    val cast: List<String>,
    val isTrending: Boolean = false,
    val isNewRelease: Boolean = false,
    val backdropColorStart: Long = 0xFF141E30,
    val backdropColorEnd: Long = 0xFF243B55,
    val seasons: List<Season> = emptyList(),
    val downloadSizes: Map<String, String> = mapOf(
        "4K UHD" to "3.4 GB",
        "1080p FHD" to "1.4 GB",
        "720p HD" to "680 MB",
        "480p SD" to "320 MB"
    )
)
