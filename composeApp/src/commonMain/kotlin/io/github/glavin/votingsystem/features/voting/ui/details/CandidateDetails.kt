package io.github.glavin.votingsystem.features.voting.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CandidateDetailsRoot(
    viewModel: CandidateDetailsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CandidateDetailsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CandidateDetailsScreen(
    state: CandidateDetailsState,
    onAction: (CandidateDetailsAction) -> Unit,
) {

}