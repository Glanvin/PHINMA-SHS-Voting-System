package io.github.glavin.votingsystem.features.auth.data.repository

import io.github.glavin.votingsystem.features.auth.data.datastore.LocalDataStoreSource
import io.github.glavin.votingsystem.features.auth.domain.AuthDataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreRepository(
    private val localDataStore: LocalDataStoreSource
): AuthDataStoreRepository {
    override suspend fun saveSessionToken(session: String): Boolean {
        return localDataStore.saveSessionToken(session)
    }

    override suspend fun clearSessionToken(): Boolean {
        return localDataStore.clearSessionToken()
    }

    override fun readSessionToken(): Flow<String> {
        return localDataStore.readSessionToken()
    }
}