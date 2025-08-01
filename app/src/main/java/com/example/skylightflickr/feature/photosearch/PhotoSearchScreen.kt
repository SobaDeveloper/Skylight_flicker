package com.example.skylightflickr.feature.photosearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.models.Photo
import com.example.skylightflickr.R
import com.example.skylightflickr.feature.common.ViewState
import com.example.skylightflickr.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchScreen(
    onPhotoClick: (Photo) -> Unit
) {
    val viewModel: PhotoSearchViewModel = koinViewModel()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewState) {
        if (viewState is ViewState.Error) {
            val message = (viewState as ViewState.Error).message
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
                        onSearch = { viewModel.searchPhotos(searchQuery.trim()) }
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
            when (val state = viewState) {
                is ViewState.Success -> {
                    if (state.data.isEmpty()) {
                        PhotoSearchEmptyState(message = stringResource(R.string.photo_search_no_results_text))
                    } else {
                        PhotoGrid(
                            photos = state.data,
                            onPhotoClick = onPhotoClick,
                        )
                    }
                }

                is ViewState.Idle -> {
                    PhotoSearchEmptyState(message = stringResource(R.string.photo_search_empty_state_text))
                }

                is ViewState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(Spacing.md))
                            Text(stringResource(R.string.photo_search_loading_text))
                        }
                    }
                }

                is ViewState.Error -> {
                    // No need to implement because already handled by Snackbar
                }
            }
        }
    }
}