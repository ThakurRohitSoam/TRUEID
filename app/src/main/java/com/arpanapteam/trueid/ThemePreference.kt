package com.arpanapteam.trueid


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore instance on Context
private val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")

object ThemePreference {

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")

    // Flow to observe current theme
    fun themeFlow(context: Context): Flow<Boolean> =
        context.themeDataStore.data.map { prefs ->
            prefs[DARK_THEME_KEY] ?: false   // default = light theme
        }

    // Save theme
    suspend fun saveTheme(context: Context, isDarkTheme: Boolean) {
        context.themeDataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = isDarkTheme
        }
    }
}
