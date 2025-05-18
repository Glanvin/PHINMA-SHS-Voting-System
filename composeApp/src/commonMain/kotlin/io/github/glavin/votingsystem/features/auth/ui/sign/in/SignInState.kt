package io.github.glavin.votingsystem.features.auth.ui.sign.`in`

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val forgotPassword: Boolean = false,
    val isEmailInvalid: Boolean = false,
    val isPasswordInvalid: Boolean = false
)
