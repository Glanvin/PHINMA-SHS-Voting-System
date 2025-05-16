package io.github.glavin.votingsystem.features.auth.ui.verification.id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class SchoolIDViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SchoolIDState())
    val state = _state
        .onStart {
            if(!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SchoolIDState()
        )
        
        fun onAction(action: SchoolIDAction) {
            when(action) {
                else -> TODO("Handle actions")
            }
        }

}