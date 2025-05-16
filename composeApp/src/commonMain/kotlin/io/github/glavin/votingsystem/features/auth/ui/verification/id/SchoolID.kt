 package io.github.glavin.votingsystem.features.auth.ui.verification.id

 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SchoolIDRoot(
    viewModel: SchoolIDViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    SchoolIDScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SchoolIDScreen(
    state: SchoolIDState,
    onAction: (SchoolIDAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}