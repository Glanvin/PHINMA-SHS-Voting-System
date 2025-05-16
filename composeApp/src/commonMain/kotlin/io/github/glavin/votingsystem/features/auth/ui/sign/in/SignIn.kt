package io.github.glavin.votingsystem.features.auth.ui.sign.`in`

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.app_name
import chiro.composeapp.generated.resources.compose_multiplatform
import chiro.composeapp.generated.resources.forgot_password
import chiro.composeapp.generated.resources.sign_in
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.core.ui.ObserveAsEvents
import io.github.glavin.votingsystem.features.auth.ui.components.DynamicLayout
import io.github.glavin.votingsystem.features.auth.ui.components.EmailOutlinedTextField
import io.github.glavin.votingsystem.features.auth.ui.components.PasswordOutlinedTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInScreenRoot(
    viewModel: SignInViewModel,
    onSuccess: () -> Unit = {},
    onForgetPassword: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    SignInScreenContent(
        viewModel = viewModel,
        state = state,
        scope = scope,
        onSuccess = onSuccess,
        onForgetPassword = onForgetPassword
    )
}

@Composable
private fun SignInScreenContent(
    viewModel: SignInViewModel,
    state: SignInState,
    scope: CoroutineScope,
    onSuccess: () -> Unit,
    onForgetPassword: () -> Unit
) {
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(
        flow = SnackbarController.snackbarEvents
    ) { events ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = events.message,
                actionLabel = events.action?.name,
                duration = events.duration
            )

            if (result == SnackbarResult.ActionPerformed) {
                events.action?.action?.invoke()
            }
        }
    }

    LaunchedEffect(state.forgotPassword) {
        if(state.forgotPassword) {
            onForgetPassword()
        }
    }

    LaunchedEffect(state.isAuthenticated) {
        if(state.isAuthenticated) {
            onSuccess()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        DynamicLayout(
            windowWidthSizeClass = windowWidthSizeClass,
            text = stringResource(Res.string.app_name),
            logo = {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = stringResource(Res.string.app_name)
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (state.isLoading && !state.isAuthenticated) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Checking your session...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    EmailOutlinedTextField(
                        email = state.email,
                        onEmailChange = { viewModel.onAction(SignInAction.Email(it)) },
                        modifier = Modifier
                            .width(320.dp)
                            .heightIn(min = 55.dp),
                        enabled = !state.isLoading,
                        isError = state.isNotEmailValid
                    )

                    if (state.isNotEmailValid) {
                        Text(
                            text = "Please enter a valid email",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 4.dp)
                                .align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    PasswordOutlinedTextField(
                        password = state.password,
                        onPasswordChange = { viewModel.onAction(SignInAction.Password(it)) },
                        modifier = Modifier
                            .width(320.dp)
                            .heightIn(min = 55.dp),
                        enabled = !state.isLoading,
                        isPasswordVisible = state.isPasswordVisible,
                        onTogglePasswordVisibility = { viewModel.onAction(SignInAction.TogglePasswordVisibility) },
                        isError = state.isNotPasswordValid
                    )

                    if (state.isNotPasswordValid) {
                        Text(
                            text = "Password must be at least 4 characters",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 4.dp)
                                .align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    FilledTonalButton(
                        onClick = { viewModel.onAction(SignInAction.SignIn) },
                        modifier = Modifier
                            .width(155.dp)
                            .heightIn(max = 45.dp),
                        enabled = state.canSignIn && !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(text = stringResource(Res.string.sign_in))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { viewModel.onAction(SignInAction.ForgotPassword) },
                    ) {
                        Text(text = stringResource(Res.string.forgot_password))
                    }
                }
            }
        }
    }
}