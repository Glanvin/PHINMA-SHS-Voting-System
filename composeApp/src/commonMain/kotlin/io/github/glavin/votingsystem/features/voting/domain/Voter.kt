package io.github.glavin.votingsystem.features.voting.domain

data class Voter(
    val id: String,
    val firstName: String,
    val lastName: String,
    val schoolEmail: String,
    val studentId: String,
    val strand: String,
    val grade: Int?,
    val voted: Boolean
)