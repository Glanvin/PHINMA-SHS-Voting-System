package io.github.glavin.votingsystem.features.voting.ui.list

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface CandidateListAction {
    object Loading: CandidateListAction
    @OptIn(ExperimentalUuidApi::class)
    data class CandidateSelected(val id: Uuid): CandidateListAction
    data class FocusedTab(var tab: Int): CandidateListAction
}