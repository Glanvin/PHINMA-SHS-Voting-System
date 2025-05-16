package io.github.glavin.votingsystem.features.auth.data.mapper

import io.github.glavin.votingsystem.features.auth.data.dto.AuthDto
import io.github.glavin.votingsystem.features.auth.domain.Auth
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun AuthDto.toAuth(): Auth {
    return Auth(
        id = id,
        sessionToken = sessionToken,
        refreshToken = refreshToken
    )
}