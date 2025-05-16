package io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password

sealed interface ForgotPasswordAction {
    data object Idle: ForgotPasswordAction
    data object Submit: ForgotPasswordAction
    data class Email(val email: String): ForgotPasswordAction
    data class NewPassword(val password: String): ForgotPasswordAction
    data class ConfirmPassword(val password: String): ForgotPasswordAction
}