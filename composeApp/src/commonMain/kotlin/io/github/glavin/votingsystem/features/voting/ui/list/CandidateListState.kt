@file:OptIn(ExperimentalUuidApi::class)

package io.github.glavin.votingsystem.features.voting.ui.list

import io.github.glavin.votingsystem.core.ui.UiText
import io.github.glavin.votingsystem.features.voting.domain.Candidate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class CandidateListState(
    val candidates: List<Candidate> = emptyList(),
    val selectedCandidate: Uuid? = null,
    val errorMessage: UiText? = null,
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
)
