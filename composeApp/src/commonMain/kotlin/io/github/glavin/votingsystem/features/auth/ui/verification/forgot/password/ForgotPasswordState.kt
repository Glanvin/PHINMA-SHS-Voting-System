package io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password

data class ForgotPasswordState(
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isNotEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = true,
    val isConfirmPasswordVisible: Boolean = false,
    val isSamePassword: Boolean = false,
    val isPasswordChangedSuccessfully: Boolean = false
)