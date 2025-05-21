package io.github.glavin.votingsystem.features.auth.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class UserProfile(
    val id: Uuid,
    val schoolId: String,
    val firstName: String,
    val lastName: String,
    val gradeLevel: Int,
    val strand: String,
    val hasVoted: Boolean,
    val accountType: AccountType,
    val verified: Boolean
)

