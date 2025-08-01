package com.example.skylightflickr.feature.photosearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.Photo
import com.example.skylightflickr.R
import com.example.skylightflickr.ui.components.FullScreenLoadingIndicator
import com.example.skylightflickr.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchScreen(
    onPhotoClick: (Photo) -> Unit
) {
    val viewModel: PhotoSearchViewModel = koinViewModel()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val photos = viewModel.photosFlow.collectAsLazyPagingItems()
    val loadState = photos.loadState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(loadState.refresh) {
        if (loadState.refresh is LoadState.Error) {
            val message = (loadState.refresh as LoadState.Error).error.localizedMessage ?: "Unknown error"
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    PhotoSearchBar(
                        query = searchQuery,
                        onQueryChange = viewModel::updateSearchQuery,
                        onSearch = viewModel::startSearch
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(Spacing.md)
        ) {
            when {
                loadState.refresh is LoadState.Loading -> {
                    FullScreenLoadingIndicator()
                }

                photos.itemCount == 0 -> {
                    PhotoSearchEmptyState(
                        message = stringResource(R.string.photo_search_no_results_text)
                    )
                }

                else -> {
                    PhotoGrid(
                        photosPagingItems = photos,
                        onPhotoClick = onPhotoClick
                    )
                }
            }
        }
    }
}