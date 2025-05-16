@file:OptIn(ExperimentalUuidApi::class)

package io.github.glavin.votingsystem.features.voting.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CandidateListViewModel : ViewModel() {

    private val _state = MutableStateFlow(CandidateListState())
    val state = _state
        .onStart {

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    @OptIn(ExperimentalUuidApi::class)
    fun onAction(action: CandidateListAction) {
        when (action) {
            is CandidateListAction.FocusedTab -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = action.tab
                    )
                }
            }
            is CandidateListAction.Loading -> {

            }
            is CandidateListAction.CandidateSelected -> {
                _state.update {
                    it.copy(
                        selectedCandidate = action.id
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun selectedCandidate(id: Uuid) {

    }
}
