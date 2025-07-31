package com.example.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    /**
     * Converts Unix timestamp to "MMM dd, yyyy"
     */
    fun formatPostedDate(timestampSeconds: String): String? {
        return try {
            val millis = timestampSeconds.toLong() * 1000
            outputFormat.format(Date(millis))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Converts "yyyy-MM-dd HH:mm:ss" date string to "MMM dd, yyyy"
     */
    fun formatTakenDate(takenDateTime: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val date = inputFormat.parse(takenDateTime)
            date?.let { outputFormat.format(it) }
        } catch (e: Exception) {
            null
        }
    }

    private val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
}