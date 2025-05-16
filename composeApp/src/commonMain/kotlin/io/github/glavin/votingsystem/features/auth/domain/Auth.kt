package io.github.glavin.votingsystem.features.auth.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Auth(
    val id: Uuid,
    val sessionToken: String,
    val refreshToken: String?,
)
