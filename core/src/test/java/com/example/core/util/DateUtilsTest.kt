package com.example.core.util

import com.example.core.util.DateUtils.formatPostedDate
import com.example.core.util.DateUtils.formatTakenDate
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class DateUtilsTest {

    @Test
    fun formatPostedDate_withValidTimestamp_returnsFormattedDate() {
        val timestamp = "1673784000"
        val result = formatPostedDate(timestamp)
        assertEquals("Jan 15, 2023", result)
    }

    @Test
    fun formatPostedDate_withInvalidTimestamp_returnsNull() {
        val invalidTimestamp = "invalid"
        val result = formatPostedDate(invalidTimestamp)
        assertNull(result)
    }

    @Test
    fun formatTakenDate_withValidDateTime_returnsFormattedDate() {
        val dateTime = "2023-06-15 14:30:45"
        val result = formatTakenDate(dateTime)
        assertEquals("Jun 15, 2023", result)
    }

    @Test
    fun formatTakenDate_withInvalidFormat_returnsNull() {
        val invalidDateTime = "2023-06-15"
        val result = formatTakenDate(invalidDateTime)
        assertNull(result)
    }
}