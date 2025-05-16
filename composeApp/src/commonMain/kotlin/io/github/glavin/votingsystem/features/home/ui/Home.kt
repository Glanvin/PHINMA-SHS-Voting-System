package io.github.glavin.votingsystem.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import io.github.glavin.votingsystem.features.home.ui.component.StudentProfileCard

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {

    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
            Alignment.BottomCenter else Alignment.Center
    ) {

    }
}