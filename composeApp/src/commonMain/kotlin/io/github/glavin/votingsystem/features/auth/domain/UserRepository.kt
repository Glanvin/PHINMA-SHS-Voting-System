package io.github.glavin.votingsystem.features.auth.domain

import io.github.glavin.votingsystem.features.auth.data.dto.AuthDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserRepository {
    suspend fun signIn(signInEmail: String, signInPassword: String): Result<Auth>
    suspend fun signInWithToken(token: String): Result<Auth>
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getUserProfile(userId: Uuid): Result<UserProfile>
}