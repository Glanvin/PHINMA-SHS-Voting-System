package io.github.glavin.votingsystem.features.voting.domain

data class Vote(
    val id: Int,
    val candidateId: String,
    val voterId: String,
    val timestamp: Long
)