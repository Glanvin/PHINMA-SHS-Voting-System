package io.github.glavin.votingsystem.features.voting.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.student_body_officer
import chiro.composeapp.generated.resources.student_strand_officer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CandidateListScreenRoot(
    viewModel: CandidateListViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CandidateListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CandidateListScreen(
    state: CandidateListState,
    onAction: (CandidateListAction) -> Unit
) {

    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(CandidateListAction.FocusedTab(pagerState.currentPage))
    }

    Column {
        TabRow(
            selectedTabIndex = state.selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth(),

        ) {
            Tab(
                selected = state.selectedTabIndex == 0,
                onClick = { onAction(CandidateListAction.FocusedTab(0)) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.student_body_officer),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }
            Tab(
                selected = state.selectedTabIndex == 1,
                onClick = { onAction(CandidateListAction.FocusedTab(1)) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.student_strand_officer),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
        ) { pageIndex ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when(pageIndex) {
                    0 -> TODO()
                    1 -> TODO()
                }
            }
        }
    }
}
