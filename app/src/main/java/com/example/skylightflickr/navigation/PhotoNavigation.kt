package com.example.skylightflickr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skylightflickr.feature.photodetails.PhotoDetailsScreen
import com.example.skylightflickr.feature.photosearch.PhotoSearchScreen
import kotlinx.serialization.Serializable

@Serializable
object SearchScreen

@Serializable
data class PhotoDetailScreen(
    val photoId: String,
    val secret: String = ""
)

@Composable
fun PhotoNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = SearchScreen
    ) {
        composable<SearchScreen> {
            PhotoSearchScreen(
                onPhotoClick = { photo ->
                    navController.navigate(
                        PhotoDetailScreen(
                            photoId = photo.id,
                            secret = photo.secret
                        )
                    )
                }
            )
        }

        composable<PhotoDetailScreen> { backStackEntry ->
            val photoDetail = backStackEntry.arguments?.let {
                PhotoDetailScreen(
                    photoId = it.getString("photoId") ?: "",
                    secret = it.getString("secret") ?: ""
                )
            } ?: return@composable

            PhotoDetailsScreen(
                photoId = photoDetail.photoId,
                secret = photoDetail.secret,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}