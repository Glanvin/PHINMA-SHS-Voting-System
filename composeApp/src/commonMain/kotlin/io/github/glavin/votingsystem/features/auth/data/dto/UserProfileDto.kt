package io.github.glavin.votingsystem.features.auth.data.dto

import io.github.glavin.votingsystem.features.auth.domain.AccountType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class UserProfileDto(
    @SerialName("id") val id: Uuid,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("school_id") val schoolId: String,
    @SerialName("grade_level") val gradeLevel: Int,
    @SerialName("strand") val strand: String,
    @SerialName("has_voted") val hasVoted: Boolean,
    @SerialName("access_level") val accountType: AccountType,
    @SerialName("verified") val verified: Boolean
)
