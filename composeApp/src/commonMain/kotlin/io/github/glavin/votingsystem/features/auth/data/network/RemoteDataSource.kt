package io.github.glavin.votingsystem.features.auth.data.network

import io.github.glavin.votingsystem.features.auth.data.dto.AuthDto
import io.github.glavin.votingsystem.features.auth.data.dto.UserProfileDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface RemoteDataSource {
    suspend fun signIn(signInEmail: String, signInPassword: String): Result<AuthDto>
    suspend fun signInWithToken(token: String): Result<AuthDto>
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getUserProfile(userId: Uuid): Result<UserProfileDto>

}