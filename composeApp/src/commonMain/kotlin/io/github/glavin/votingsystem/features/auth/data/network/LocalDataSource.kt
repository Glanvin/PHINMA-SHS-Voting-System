package io.github.glavin.votingsystem.features.auth.data.network

import io.github.glavin.votingsystem.features.auth.data.dto.UserProfileDto

interface LocalDataSource {
    suspend fun signIn(signInEmail: String, signInPassword: String): Result<UserProfileDto>
}