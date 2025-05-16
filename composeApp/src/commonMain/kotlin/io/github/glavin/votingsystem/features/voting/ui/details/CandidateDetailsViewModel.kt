package io.github.glavin.votingsystem.features.voting.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class CandidateDetailsViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CandidateDetailsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CandidateDetailsState()
        )

    fun onAction(action: CandidateDetailsAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}