package io.github.glavin.votingsystem.features.auth.data.mapper

import io.github.glavin.votingsystem.features.auth.data.dto.UserProfileDto
import io.github.glavin.votingsystem.features.auth.domain.UserProfile
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
        id = id,
        schoolId = schoolId,
        firstName = firstName,
        lastName = lastName,
        gradeLevel = gradeLevel,
        accountType = accountType,
        hasVoted = hasVoted,
        imageUrl = imageUrl,
        strand = strand,
        verified = verified,
    )
}