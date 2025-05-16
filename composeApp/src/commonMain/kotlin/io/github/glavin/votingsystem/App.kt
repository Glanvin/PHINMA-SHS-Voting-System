package io.github.glavin.votingsystem

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.glavin.votingsystem.core.Main

@Composable
fun App(
    dynamicColor: Boolean = false,
) {
    Main()
}

