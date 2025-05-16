@file:OptIn(ExperimentalUuidApi::class)

package io.github.glavin.votingsystem.features.voting.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class CandidateDto(
    @SerialName("id") val id: Uuid,
    @SerialName("school_id") val schoolId: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("grade_level") val gradeLevel: Int,
    @SerialName("strand") val strand: String,
    @SerialName("party") val party: String,
    @SerialName("running_for") val position: String,
)
