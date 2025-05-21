 package io.github.glavin.votingsystem.features.auth.ui.verification.id

 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.heightIn
 import androidx.compose.foundation.layout.size
 import androidx.compose.foundation.layout.width
 import androidx.compose.material3.Button
 import androidx.compose.material3.CircularProgressIndicator
 import androidx.compose.material3.LocalTextStyle
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.OutlinedTextField
 import androidx.compose.material3.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.compose.runtime.collectAsState
 import androidx.compose.runtime.getValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.text.style.TextAlign
 import androidx.compose.ui.unit.dp
 import androidx.lifecycle.compose.collectAsStateWithLifecycle

 @Composable
fun SchoolIDRoot(
     viewModel: SchoolIDViewModel,
     onHasIncompleteProfile: () -> Unit,
     onSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
     val event by viewModel.event.collectAsState(SchoolIDAction.Idle)

    SchoolIDScreen(
        state = state,
        event = event,
        onSuccess = onSuccess,
        onHasIncompleteProfile = onHasIncompleteProfile,
        onAction = viewModel::onAction
    )
}

@Composable
fun SchoolIDScreen(
    state: SchoolIDState,
    event: SchoolIDAction,
    onSuccess: () -> Unit,
    onHasIncompleteProfile: () -> Unit,
    onAction: (SchoolIDAction) -> Unit,
) {

    val enabled = !state.isLoading && state.schoolId.isNotBlank() && !state.isFormatInvalid

    LaunchedEffect(event) {
        when (event) {
            is SchoolIDAction.NewVoter -> onHasIncompleteProfile()
            is SchoolIDAction.Verified -> onSuccess()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            modifier = Modifier
                .width(180.dp)
                .heightIn(55.dp),
            value = state.schoolId,
            onValueChange = { onAction(SchoolIDAction.SchoolIdNumber(it)) },
            label = { Text(text = "School ID") },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            supportingText = {
                if (state.isSchoolIdNotFound) {
                    Text(text = " School ID doesn't exist")
                }

                if (state.isFormatInvalid) {
                    Text(text = "Invalid Format")
                }
            },
            isError = state.isSchoolIdNotFound || state.isFormatInvalid
        )

        Button(
            onClick = { onAction(SchoolIDAction.Submit) },
            enabled = enabled
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(text = "Submit")
            }
        }
    }
}