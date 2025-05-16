package io.github.glavin.votingsystem.features.home.domain

import io.github.glavin.votingsystem.features.auth.domain.AccountType

data class User(
    val schoolId: String,
    val firstName: String,
    val lastName: String,
    val grade: Int,
    val strand: String,
    val profilePicture: String,
    val accountType: AccountType,
    val hasVoted: Boolean,
    val isNewUser: Boolean
)
