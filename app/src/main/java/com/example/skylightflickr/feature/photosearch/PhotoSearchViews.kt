package com.example.skylightflickr.feature.photosearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.domain.models.Photo
import com.example.skylightflickr.R
import com.example.skylightflickr.ui.components.InlineLoadingIndicator
import com.example.skylightflickr.ui.theme.Spacing

@Composable
fun PhotoSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text(stringResource(R.string.photo_search_app_bar_label)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(end = Spacing.md),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                keyboardController?.hide()
            }
        ),
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.photo_search_clear_content_description)
                    )
                }
            }
        }
    )
}


@Composable
fun PhotoGrid(
    photosPagingItems: LazyPagingItems<Photo>? = null,
    onPhotoClick: (Photo) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        verticalItemSpacing = Spacing.xs,
        modifier = Modifier.fillMaxSize()
    ) {
        if (photosPagingItems != null) {
            items(
                count = photosPagingItems.itemCount,
                key = { index -> photosPagingItems.peek(index)?.id ?: "loading-$index" }
            ) { index ->
                val photo = photosPagingItems[index]
                if (photo != null) {
                    PhotoGridItem(photo = photo, onClick = { onPhotoClick(photo) })
                } else {
                    PhotoGridItemPlaceholder()
                }
            }

            when (val appendState = photosPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        LoadingIndicator(message = stringResource(R.string.photo_search_loading_more))
                    }
                }

                is LoadState.NotLoading -> {
                    if (photosPagingItems.itemCount > 0 && appendState.endOfPaginationReached) {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            ResultsIndicator(stringResource(R.string.photo_search_no_results))
                        }
                    }
                }

                is LoadState.Error -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        ResultsIndicator(stringResource(R.string.photo_search_loading_error))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoGridItem(
    photo: Photo,
    onClick: () -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        GlideImage(
            model = photo.imageUrl,
            contentDescription = photo.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun PhotoGridItemPlaceholder() {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        InlineLoadingIndicator()
    }
}


@Composable
fun PhotoSearchEmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.xl),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LoadingIndicator(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.md),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            InlineLoadingIndicator()
            Spacer(modifier = Modifier.height(Spacing.sm))
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ResultsIndicator(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.md),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}