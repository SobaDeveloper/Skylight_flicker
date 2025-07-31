package com.example.skylightflickr.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "search_prefs")

class SearchPrefsManager(private val context: Context) {

    private val keySearchQuery = stringPreferencesKey("search_query")

    val searchQueryFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[keySearchQuery] ?: ""
        }

    suspend fun saveSearchQuery(query: String) {
        context.dataStore.edit { preferences ->
            preferences[keySearchQuery] = query
        }
    }
}