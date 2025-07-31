package com.example.skylightflickr.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.skylightflickr.navigation.FlickrNavigation
import com.example.skylightflickr.ui.theme.SkylightFlickrTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkylightFlickrTheme {
                FlickrNavigation()
            }
        }
    }
}
