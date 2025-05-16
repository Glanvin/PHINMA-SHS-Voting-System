package io.github.glavin.votingsystem.features.auth.ui.sign.`in`

sealed interface SignInAction {
    data object Idle: SignInAction
    data object SignIn: SignInAction
    data object ForgotPassword: SignInAction
    data object TogglePasswordVisibility: SignInAction
    data class Email(val email: String): SignInAction
    data class Password(val password: String): SignInAction
}