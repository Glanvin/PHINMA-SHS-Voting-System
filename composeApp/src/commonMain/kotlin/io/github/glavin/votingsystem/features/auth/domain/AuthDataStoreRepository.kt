package io.github.glavin.votingsystem.features.auth.domain

import kotlinx.coroutines.flow.Flow

interface AuthDataStoreRepository {
    suspend fun saveSessionToken(session: String): Boolean
    suspend fun clearSessionToken(): Boolean
    fun readSessionToken(): Flow<String>
}