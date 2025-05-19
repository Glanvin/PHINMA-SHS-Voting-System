package io.github.glavin.votingsystem.features.auth.ui.sign.out

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.core.ui.SnackbarEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignOutViewModel(
    private val snackbarController: SnackbarController
) : ViewModel() {

    private val _state = MutableStateFlow(SignOutState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SignOutState())

    private val _event: Channel<SignOutAction> = Channel()
    val event = _event.receiveAsFlow()

    fun onAction(action: SignOutAction) {
        when (action) {
            is SignOutAction.SignOut -> signOut()
            is SignOutAction.DialogClose -> _state.update { it.copy(showDialog = false) }
            is SignOutAction.DialogOpen -> _state.update { it.copy(showDialog = true) }
            else -> Unit
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(2000)
            _state.update { it.copy(showDialog = false, isLoading = false) }
            _event.send(SignOutAction.Success)
            snackbarController.sendEvent(SnackbarEvent("Successfully Signed Out!"))
        }
    }

}