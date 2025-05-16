package io.github.glavin.votingsystem.features.voting.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.glavin.votingsystem.features.voting.domain.Candidate
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListAction
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CandidateList(
    modifier: Modifier = Modifier,
    candidates: List<Candidate>,
    state: CandidateListState,
    onAction: (CandidateListAction) -> Unit,
) {
    LazyColumn(
        modifier
    ) {
        items(candidates, key = { it.id }) { candidate ->
            CandidateListCardItem(
                candidate = candidate,
                cardSize = 200.dp,
                profileSize = 120.dp,
                onClick = {
                    println("Candidate ID:${it.id}")
                    println("Candidate Name:${it.firstName} ${it.lastName}")
                }
            )
        }
    }
}