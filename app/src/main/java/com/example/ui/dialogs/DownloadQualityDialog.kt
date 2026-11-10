package com.example.ui.dialogs

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.example.model.Episode
import com.example.model.MediaItem
import com.example.ui.theme.CinematicBluePrimary
import com.example.ui.theme.CinematicCardBorder
import com.example.ui.theme.CinematicCyan
import com.example.ui.theme.CinematicGreen
import com.example.ui.theme.CinematicSurface
import com.example.ui.theme.CinematicTextMuted
import com.example.ui.theme.CinematicTextPrimary
import com.example.ui.theme.CinematicTextSecondary

data class QualityOption(
    val label: String,
    val resolution: String,
    val size: String,
    val isRecommended: Boolean = false
)

@Composable
fun DownloadQualityDialog(
    media: MediaItem,
    episode: Episode? = null,
    onDismiss: () -> Unit,
    onConfirmDownload: (String) -> Unit
) {
    val qualityOptions = listOf(
        QualityOption("4K UHD", "2160p • Dolby Atmos", if (episode != null) "1.2 GB" else "3.4 GB"),
        QualityOption("1080p FHD", "Full HD • 5.1 Surround", if (episode != null) "540 MB" else "1.4 GB", isRecommended = true),
        QualityOption("720p HD", "Standard HD • Stereo", if (episode != null) "280 MB" else "680 MB"),
        QualityOption("480p SD", "Data Saver • Mobile", if (episode != null) "150 MB" else "350 MB")
    )

    var selectedQuality by remember { mutableStateOf("1080p FHD") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CinematicSurface,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    tint = CinematicBluePrimary,
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(
                        text = "High-Speed Offline Download",
                        color = CinematicTextPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (episode != null) "${media.title} (Ep ${episode.episodeNumber})" else media.title,
                        color = CinematicTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // High Speed Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CinematicGreen.copy(alpha = 0.15f))
                        .border(1.dp, CinematicGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = CinematicGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Turbo High-Speed Multi-Thread Engine Active",
                        color = CinematicGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "SELECT DOWNLOAD RESOLUTION:",
                    color = CinematicTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                qualityOptions.forEach { opt ->
                    val isSelected = selectedQuality == opt.label
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CinematicBluePrimary.copy(alpha = 0.15f) else Color.Transparent)
                            .border(1.dp, if (isSelected) CinematicBluePrimary else CinematicCardBorder, RoundedCornerShape(8.dp))
                            .clickable { selectedQuality = opt.label }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedQuality = opt.label },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = CinematicBluePrimary,
                                    unselectedColor = CinematicTextMuted
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = opt.label,
                                        color = CinematicTextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (opt.isRecommended) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(3.dp))
                                                .background(CinematicCyan.copy(alpha = 0.2f))
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        ) {
                                            Text(
                                                text = "POPULAR",
                                                color = CinematicCyan,
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = opt.resolution,
                                    color = CinematicTextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Text(
                            text = opt.size,
                            color = CinematicTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirmDownload(selectedQuality) },
                colors = ButtonDefaults.buttonColors(containerColor = CinematicBluePrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("start_download_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Download Now",
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = CinematicTextSecondary
                )
            }
        }
    )
}
