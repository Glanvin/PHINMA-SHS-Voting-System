package io.github.glavin.votingsystem.features.voting.domain

import io.github.glavin.votingsystem.features.voting.ui.components.Position
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Candidate(
    val id: Uuid,
    val firstName: String,
    val lastName: String,
    val position: Position,
    val strand: String?,
    val party: String,
    val grade: Int?,
    val photoUrl: String,
)