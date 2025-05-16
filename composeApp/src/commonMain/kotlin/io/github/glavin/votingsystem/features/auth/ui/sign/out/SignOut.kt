package io.github.glavin.votingsystem.features.auth.ui.sign.out

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignOutRoot(
    viewModel: SignOutViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SignOutScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SignOutScreen(
    state: SignOutState,
    onAction: (SignOutAction) -> Unit,
) {

}