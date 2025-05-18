package io.github.glavin.votingsystem.features.auth.ui.sign.`in`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.core.ui.SnackbarEvent
import io.github.glavin.votingsystem.features.auth.domain.Auth
import io.github.glavin.votingsystem.features.auth.domain.AuthDataStoreRepository
import io.github.glavin.votingsystem.features.auth.domain.UserProfile
import io.github.glavin.votingsystem.features.auth.domain.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.ExperimentalUuidApi

class SignInViewModel(
    private val repository: UserRepository,
    private val snackbarController: SnackbarController,
    private val dataStoreRepository: AuthDataStoreRepository
) : ViewModel() {

    private var emailJob: Job? = null
    private val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    private val domains = listOf("phinmaed.com")

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state
        .onStart {
            observeEmailChanges()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SignInState())

    private val _event: Channel<SignInAction> = Channel()
    val event = _event.receiveAsFlow()


    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.SignIn -> signIn()
            is SignInAction.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignInAction.Password -> _state.update { it.copy(password = action.password,) }
            is SignInAction.Email -> _state.update { it.copy(email = action.email,) }
            is SignInAction.ForgotPassword -> {
                _state.update { it.copy(forgotPassword = true) }
                forgetPassword()
            }
            else -> {}
        }
    }

    private fun forgetPassword() {
        viewModelScope.launch {
            _event.send(SignInAction.OnNavigateForgotPassword)
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
                        _event.send(SignInAction.OnNavigateNext)
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
                    onAuthenticationFailure(it.message ?: "Token Expired!")
                }
            )
        }.onFailure {
            onAuthenticationFailure(it.message ?: "An unexpected error occurred!")
        }
        return _state.value.isAuthenticated
    }

    @OptIn(FlowPreview::class)
    private fun observeEmailChanges() {
        viewModelScope.launch {
            state
                .map { it.email }
                .distinctUntilChanged()
                .debounce(1.seconds)
                .collect { email ->
                    when {
                        email.isEmpty() -> _state.update { it.copy(isEmailInvalid = false) }
                        email.length <= 2 && !email.contains('@', true) -> {
                            _state.update { it.copy(isEmailInvalid = true) }
                            snackbarController.sendEvent(SnackbarEvent("Email to short"))
                        }
                        else -> {
                            emailJob?.cancel()
                            emailJob = checkEmail(email)
                        }
                    }
                }
        }
    }

    private fun checkEmail(email: String) = viewModelScope.launch {
        val (isValid, errorMessage) = isEmailFormattedCorrectly(email)
        if (!isValid) {
            _state.update { it.copy(isEmailInvalid = !isValid) }
            snackbarController.sendEvent(SnackbarEvent(message = errorMessage))
        }
    }

    private fun isEmailFormattedCorrectly(email: String): Pair<Boolean, String> {
        if(!emailRegex.matches(email)) {
            return Pair(false, "Please Enter an valid Email")
        }

        val domain = email.substringAfterLast('@', "")
        if(domains.isNotEmpty() && domain !in domains) {
            return Pair(false, "This domain isn't registered please contact the administrator")
        }

        return Pair(true, "")
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    private fun resetCredentials() {
        _state.update {
            it.copy(
                email = "",
                password = ""
            )
        }
    }

    private suspend fun onAuthenticationSuccess(auth: Auth) {
        _state.update { it.copy(isLoading = false, isAuthenticated = true) }
        retrieveUserProfile(auth).fold(
            onSuccess = {
                val firstName = it.firstName.split(" ")[0]
                snackbarController.sendEvent(
                    SnackbarEvent(message = "Sign In Successfully, Hello, $firstName")
                )
                resetCredentials()
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
    private suspend fun retrieveUserProfile(auth: Auth): Result<UserProfile> {
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