package io.github.glavin.votingsystem.features.auth.data.datastore

import kotlinx.coroutines.flow.Flow

interface LocalDataStoreSource {
    suspend fun saveSessionToken(session: String): Boolean
    suspend fun clearSessionToken(): Boolean
    fun readSessionToken(): Flow<String>
}