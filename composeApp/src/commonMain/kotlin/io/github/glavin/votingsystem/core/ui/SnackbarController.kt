package io.github.glavin.votingsystem.core.ui

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short
)

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

object SnackbarController {

    private val _snackbarEvents = Channel<SnackbarEvent>()
    val snackbarEvents = _snackbarEvents.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _snackbarEvents.send(event)
    }
}
