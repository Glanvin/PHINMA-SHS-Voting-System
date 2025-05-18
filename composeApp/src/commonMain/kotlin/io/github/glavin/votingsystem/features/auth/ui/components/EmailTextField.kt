package io.github.glavin.votingsystem.features.auth.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.email
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailOutlinedTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = email,
        onValueChange = onEmailChange,
        label = {
            Text(
                text = stringResource(Res.string.email)
            )
        },
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = if(email.isNotEmpty()) ImeAction.Next else ImeAction.Done
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = "Invalid Email")
            }
        }
    )
}