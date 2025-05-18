package io.github.glavin.votingsystem.features.auth.ui.sign.`in`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.core.ui.SnackbarEvent
import io.github.glavin.votingsystem.features.auth.domain.Auth
import io.github.glavin.votingsystem.features.auth.domain.AuthDataStoreRepository
import io.github.glavin.votingsystem.features.auth.domain.UserProfile
import io.github.glavin.votingsystem.features.auth.domain.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class SignInViewModel(
    private val repository: UserRepository,
    private val snackbarController: SnackbarController,
    private val dataStoreRepository: AuthDataStoreRepository
) : ViewModel() {

    private var _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state
        .onStart {
            checkSessionExisting()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SignInState())

    private val _event: Channel<SignInAction> = Channel()
    val event = _event.receiveAsFlow()


    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.SignIn -> signIn()
            is SignInAction.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignInAction.Email -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }

            is SignInAction.Password -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is SignInAction.ForgotPassword -> {
                _state.update {
                    it.copy(
                        forgotPassword = true
                    )
                }
            }
            else -> {}
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            kotlin.runCatching {
                val email = _state.value.email.trim()
                val password = _state.value.password
                repository.signIn(email, password)
            }.onSuccess { result ->
                result.fold(
                    onSuccess = { auth ->
                        dataStoreRepository.saveSessionToken(auth.sessionToken)
                        snackbarController.sendEvent(SnackbarEvent("Successfully Signed In!"))
                        onAuthenticationSuccess(auth)
                    },
                    onFailure = {
                        snackbarController.sendEvent(SnackbarEvent("Invalid Credentials!"))
                    }
                )
            }.onFailure {
                onAuthenticationFailure(it.message ?: "An unexpected error occurred")
            }

        }
    }

    private suspend fun checkSessionExisting() {
        dataStoreRepository.readSessionToken().collectLatest { session ->
            if (session.isNotEmpty()) {
                _state.update { it.copy(isLoading = true) }
                val success = signInWithToken(sessionToken = session)
                if (success) {
                    _event.send(SignInAction.OnNavigateNext)
                    _state.update { it.copy(isLoading = false, isAuthenticated = true) }
                }
            } else {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun signInWithToken(sessionToken: String): Boolean {
        kotlin.runCatching {
            repository.signInWithToken(sessionToken)
        }.onSuccess { result ->
            result.fold(
                onSuccess = { auth ->
                    dataStoreRepository.saveSessionToken(auth.sessionToken)
                    snackbarController.sendEvent(SnackbarEvent("Successfully Signed In!"))
                    onAuthenticationSuccess(auth)
                },
                onFailure = {
                    onAuthenticationFailure("Token is expired, Please login again")
                    false
                }
            )
            true
        } catch (e: Exception) {
            onAuthenticationFailure(message = e.message ?: "An unexpected error occurred")
            false
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(email)
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    private fun clearFields() {
        _state.update {
            it.copy(
                email = "",
                password = ""
            )
        }
    }

    private suspend fun onAuthenticationSuccess(auth: Auth) {
        _state.update { it.copy(isLoading = false, isAuthenticated = true) }
        getUser(auth).fold(
            onSuccess = {
                val firstName = it.firstName.split(" ")[0]
                snackbarController.sendEvent(
                    SnackbarEvent(message = "Sign In Successfully, Hello, $firstName")
                )
                clearFields()
            },
            onFailure = {
                snackbarController.sendEvent(
                    SnackbarEvent(message = it.message ?: "An unexpected error occurred")
                )
            }
        )
    }

    private suspend fun onAuthenticationFailure(message: String) {
        _state.update { it.copy(isLoading = false) }
        snackbarController.sendEvent(
            SnackbarEvent(message = message)
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun getUser(auth: Auth): Result<UserProfile> {
        return kotlin.runCatching {
            repository.getUserProfile(auth.id).getOrThrow()
        }.fold(
            onSuccess = {
                Result.success(it)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

}