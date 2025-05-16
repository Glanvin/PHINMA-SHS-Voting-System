package io.github.glavin.votingsystem.features.auth.ui.verification.id

data class SchoolIDState(
    val isLoading: Boolean = false,
    val schoolId: String = "",
    val error: String? = null,
    val isVerified: Boolean = false
)
