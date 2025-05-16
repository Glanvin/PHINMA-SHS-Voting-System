package io.github.glavin.votingsystem.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.glavin.votingsystem.core.ui.darkScheme
import io.github.glavin.votingsystem.core.ui.lightScheme

@Composable
actual fun ChiroTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable() () -> Unit
) {
    MaterialTheme (
        colorScheme = if(darkTheme) darkScheme else lightScheme,
        content = content
    )
}