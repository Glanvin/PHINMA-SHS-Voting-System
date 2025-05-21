package io.github.glavin.votingsystem.features.auth.ui.verification.id

sealed interface SchoolIDAction {
    data object Idle : SchoolIDAction
    data object Verified : SchoolIDAction
    data object NewVoter : SchoolIDAction
    data object HasIncompleteProfile : SchoolIDAction
    data object Submit : SchoolIDAction
    data class SchoolIdNumber(val id: String) : SchoolIDAction
}