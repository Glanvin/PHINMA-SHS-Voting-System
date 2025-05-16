package io.github.glavin.votingsystem.features.auth.data.repository

import io.github.glavin.votingsystem.features.auth.data.dto.AuthDto
import io.github.glavin.votingsystem.features.auth.data.mapper.toAuth
import io.github.glavin.votingsystem.features.auth.data.mapper.toUserProfile
import io.github.glavin.votingsystem.features.auth.data.network.RemoteDataSource
import io.github.glavin.votingsystem.features.auth.domain.Auth
import io.github.glavin.votingsystem.features.auth.domain.UserProfile
import io.github.glavin.votingsystem.features.auth.domain.UserRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SignInRepository(
    private val remoteDataSource: RemoteDataSource
): UserRepository {
    override suspend fun signIn(signInEmail: String, signInPassword: String): Result<Auth> {
        return remoteDataSource.signIn(signInEmail, signInPassword)
            .map {
                it.toAuth()
            }
    }

    override suspend fun signInWithToken(token: String): Result<Auth> {
        return remoteDataSource.signInWithToken(token)
            .map {
                it.toAuth()
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getUserProfile(userId: Uuid): Result<UserProfile> {
        return remoteDataSource.getUserProfile(userId)
            .map {
                it.toUserProfile()
            }
    }
}