package io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ForgotPasswordState()
        )

    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            is ForgotPasswordAction.Email -> {
                _state.update {
                    it.copy(
                        email = action.email
                    )
                }
            }
            is ForgotPasswordAction.NewPassword -> {
                _state.update {
                    it.copy(
                        newPassword = action.password
                    )
                }
            }
            is ForgotPasswordAction.ConfirmPassword -> {
                _state.update {
                    it.copy(
                        confirmPassword = action.password
                    )
                }
            }
            else -> {}
        }
    }

}