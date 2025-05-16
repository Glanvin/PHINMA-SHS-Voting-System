package io.github.glavin.votingsystem.features.auth.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.password
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordOutlinedTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    isPasswordVisible: Boolean = false,
    onTogglePasswordVisibility: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = onPasswordChange,
        label = {
            Text(
                text = stringResource(Res.string.password)
            )
        },
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = if(password.isNotEmpty()) ImeAction.Done else ImeAction.None
        ),
        trailingIcon = { PasswordVisibilityButton(password, isPasswordVisible, onTogglePasswordVisibility) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = isError
    )
}

@Composable
fun PasswordVisibilityButton(
    password: String,
    visible: Boolean = false,
    toggleVisibility: () -> Unit
) {
    IconButton(
        onClick = { toggleVisibility() },
    ) {
        if (password.isNotEmpty()) {
            val (icon, description) = when (visible) {
                true -> Icons.Filled.Visibility to "Hide password"
                false -> Icons.Filled.VisibilityOff to "Show password"
            }
            Icon(imageVector = icon, contentDescription = description)
        }
    }
}