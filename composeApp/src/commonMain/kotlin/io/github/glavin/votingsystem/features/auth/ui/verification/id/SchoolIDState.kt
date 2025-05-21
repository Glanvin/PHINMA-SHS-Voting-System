package io.github.glavin.votingsystem.features.auth.ui.verification.id

data class SchoolIDState(
    val isLoading: Boolean = false,
    val schoolId: String = "",
    val isVerified: Boolean = false,
    val hasIncompleteProfile: Boolean = false,
    val isFormatInvalid: Boolean = false,
    val isSchoolIdNotFound: Boolean = false
)
