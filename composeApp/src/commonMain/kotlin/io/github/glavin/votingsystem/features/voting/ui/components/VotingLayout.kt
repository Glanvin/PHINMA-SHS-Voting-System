package io.github.glavin.votingsystem.features.voting.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowWidthSizeClass
import io.github.glavin.votingsystem.features.voting.domain.Candidate
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListAction
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListState

@Composable
fun DynamicVotingLayout(
    candidates: List<Candidate>,
    content: @Composable () -> Unit
) {
    content()
}

@Composable
fun CandidateListScreen(
    candidates: List<Candidate>,
    lazyListSate: LazyListState,
    windowWidthSizeClass: WindowWidthSizeClass
) {

}

@Composable
private fun CandidateListPortrait(
    candidates: List<Candidate>,
    lazyListSate: LazyListState,
    state: CandidateListState,
    onAction: (CandidateListAction) -> Unit
) {

}

@Composable
private fun CandidateListLandscape(
    candidates: List<Candidate>,
    lazyListSate: LazyListState,
    onAction: (CandidateListAction) -> Unit
) {

}