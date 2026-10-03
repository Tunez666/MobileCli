package com.tunez.lifetree.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "lifetree_preferences"
)

class TokenStorage(
    private val context: Context
) {

    private val accessTokenKey =
        stringPreferencesKey("access_token")

    val accessToken: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[accessTokenKey]
        }

    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { preferences ->
                preferences[accessTokenKey]
            }
            .first()
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
        }
    }
}