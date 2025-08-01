package com.example.skylightflickr.feature.photodetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.util.DateUtils
import com.example.domain.models.PhotoDetails
import com.example.skylightflickr.R
import com.example.skylightflickr.ui.theme.Spacing


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoDetailsContent(
    photoDetails: PhotoDetails
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Spacing.md)
    ) {
        GlideImage(
            model = photoDetails.imageUrl,
            contentDescription = photoDetails.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        PhotoDetailsBottomSheet(
            photoDetails = photoDetails,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

@Composable
private fun PhotoDetailsBottomSheet(
    photoDetails: PhotoDetails,
    modifier: Modifier = Modifier
) {
    var showDescriptionDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.8f)
                    )
                )
            )
            .padding(Spacing.md)
    ) {
        Spacer(modifier = Modifier.height(Spacing.xl))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (photoDetails.title.isNotEmpty()) {
                    Text(
                        text = photoDetails.title,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(Spacing.sm))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.md)
                ) {
                    DateUtils.formatTakenDate(photoDetails.dateTaken)?.let { formattedDate ->
                        PhotoDetailItem(
                            label = stringResource(R.string.taken),
                            value = formattedDate
                        )
                    }
                    DateUtils.formatPostedDate(photoDetails.datePosted)?.let { formattedDate ->
                        PhotoDetailItem(
                            label = stringResource(R.string.posted),
                            value = formattedDate
                        )
                    }
                }
            }

            if (photoDetails.description.isNotEmpty()) {
                IconButton(
                    onClick = { showDescriptionDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(R.string.photo_detail_info_content_description),
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(Spacing.lg)
                    )
                }
            }
        }
    }

    if (showDescriptionDialog) {
        PhotoDescriptionDialog(
            title = photoDetails.title,
            description = photoDetails.description,
            onDismiss = { showDescriptionDialog = false }
        )
    }
}

@Composable
private fun PhotoDetailItem(
    label: String,
    value: String
) {
    Row {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.width(Spacing.xs))
        Text(
            text = value,
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodySmall

        )
    }
}