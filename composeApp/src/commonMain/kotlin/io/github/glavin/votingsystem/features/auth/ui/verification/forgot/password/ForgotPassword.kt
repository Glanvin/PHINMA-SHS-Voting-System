package io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.glavin.votingsystem.features.auth.ui.components.DynamicLayout
import io.github.glavin.votingsystem.features.auth.ui.components.EmailOutlinedTextField

@Composable
fun ForgotPasswordRoot(
    viewModel: ForgotPasswordViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit,
) {

    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    DynamicLayout(
        text = "Forgot Password",
        windowWidthSizeClass = windowWidthSizeClass
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EmailOutlinedTextField(
                email = state.email,
                onEmailChange = { onAction(ForgotPasswordAction.Email(it)) },
                enabled = state.isLoading,
                isError = state.isNotEmailValid
            )
        }
    }

}