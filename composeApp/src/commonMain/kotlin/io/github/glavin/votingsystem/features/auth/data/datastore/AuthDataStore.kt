package io.github.glavin.votingsystem.features.auth.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class AuthDataStore(
    private val dataStore: DataStore<Preferences>
): LocalDataStoreSource {

    companion object {
        val sessionKey = stringPreferencesKey("session_key")
    }

    override suspend fun saveSessionToken(session: String): Boolean {
        try {
            dataStore.edit { preference ->
                preference.set(key = sessionKey, value = session)
            }
            println("Session Token Saved!")
            return true
        }catch (e: Exception){
            println("Session Token Error: ${e.message}")
            return false
        }
    }

    override suspend fun clearSessionToken(): Boolean {
        try {
            dataStore.edit { preference ->
                preference.set(key = sessionKey, value = "")
            }
            println("Session Token Clear!")
            return true
        }catch (e: Exception){
            println("Session Token Error: Token was not Cleared! ${e.message}")
            return false
        }
    }

    override fun readSessionToken(): Flow<String> {
        return dataStore.data
            .catch { emptyFlow<String>() }
            .map { preference ->
                preference[sessionKey] ?: ""
            }
    }

}