package io.github.glavin.votingsystem.features.auth.ui.sign.`in`

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val forgotPassword: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
) {

    val isNotEmailValid: Boolean get() = !isEmailValid && email.isNotEmpty()
    val isNotPasswordValid: Boolean get() = !isPasswordValid && password.isNotEmpty()
    val canSignIn: Boolean get() = email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid
}
