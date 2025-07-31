package com.example.core.util

import com.example.core.model.PhotoSize

object PhotoUtils {

    fun buildImageUrl(
        farm: Int,
        server: String,
        id: String,
        secret: String,
        size: PhotoSize = PhotoSize.LARGE
    ): String {
        return "https://farm$farm.staticflickr.com/$server/${id}_${secret}_${size.suffix}.jpg"
    }
}