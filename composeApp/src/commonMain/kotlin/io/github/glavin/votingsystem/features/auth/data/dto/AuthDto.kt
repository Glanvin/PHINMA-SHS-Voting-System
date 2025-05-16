package io.github.glavin.votingsystem.features.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class AuthDto(
    @SerialName("id") val id: Uuid,
    @SerialName("session_token") val sessionToken: String,
    @SerialName("refresh_token") val refreshToken: String? = "",
)
