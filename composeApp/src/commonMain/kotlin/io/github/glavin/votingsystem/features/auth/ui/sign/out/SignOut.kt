package io.github.glavin.votingsystem.features.auth.ui.sign.out

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignOutRoot(
    viewModel: SignOutViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsState(SignOutAction.Idle)
    SignOutScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOutScreen(
    state: SignOutState,
    onAction: (SignOutAction) -> Unit,
) {

    Button(
        onClick = { onAction(SignOutAction.DialogOpen) },
    ) {
        Text(text = "Sign Out")
    }

    if (state.showDialog) {
        BasicAlertDialog(
            onDismissRequest = { onAction(SignOutAction.DialogClose) },
            content = {
                Surface(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = 8.dp,
                    shadowElevation = 3.dp
                )
                {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Want to sign out?",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "A session will be saved when you sign in again,",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2
                        )
                        Text(
                            text = "You can disable this the settings.",
                            style = MaterialTheme.typography.bodySmall,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(18.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {

                            AnimatedContent(state.isLoading) { loading ->
                                if (loading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                } else {
                                    TextButton(
                                        onClick = { onAction(SignOutAction.DialogClose) },
                                        content = {
                                            Text(
                                                text = "Cancel"
                                            )
                                        }
                                    )

                                    TextButton(
                                        onClick = { onAction(SignOutAction.SignOut) },
                                        content = {
                                            Text(
                                                text = "Confirm",
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }

}