package com.example

import com.example.data.MovieCatalog
import com.example.model.DownloadItem
import com.example.model.DownloadStatus
import com.example.model.MediaType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun catalog_containsWorldwideMoviesAndSeries() {
        val catalog = MovieCatalog.worldwideCatalog
        assertTrue("Catalog should contain titles", catalog.isNotEmpty())

        val movies = catalog.filter { it.type == MediaType.MOVIE }
        val series = catalog.filter { it.type == MediaType.SERIES }

        assertTrue("Should contain Hollywood and Bollywood movies", movies.isNotEmpty())
        assertTrue("Should contain worldwide web series", series.isNotEmpty())
    }

    @Test
    fun catalog_supports18PlusMatureContent() {
        val catalog = MovieCatalog.worldwideCatalog
        val matureTitles = catalog.filter { it.is18Plus }
        assertTrue("Catalog must include 18+ content", matureTitles.isNotEmpty())
        assertTrue(
            "Mature titles have 18+ content rating",
            matureTitles.all { it.contentRating.contains("18+") || it.contentRating.contains("TV-MA") || it.contentRating.contains("R") }
        )
    }

    @Test
    fun downloadItem_progressAndSpeedCalculations() {
        val download = DownloadItem(
            id = "d1",
            mediaId = "m1",
            title = "Dune Prophecy",
            subtitle = "S1:E1",
            quality = "1080p FHD",
            totalSizeMb = 500.0,
            downloadedMb = 250.0,
            speedMbPerSec = 25.0,
            status = DownloadStatus.DOWNLOADING
        )

        assertEquals(0.5f, download.progress, 0.001f)
        assertEquals(50, download.percentage)
        assertTrue(download.formattedRemaining.contains("MB"))
    }
}
