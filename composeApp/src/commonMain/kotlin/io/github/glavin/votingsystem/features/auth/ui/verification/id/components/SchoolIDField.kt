package io.github.glavin.votingsystem.features.auth.ui.verification.id.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SchoolID(
    value: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text("e.g. 02-1234-60192")
        },
        label = {
            Text("School ID")
        },
        isError = isError,
        singleLine = true,
        supportingText = {
            if (isError) {
                Text("Invalid School ID")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    )
}