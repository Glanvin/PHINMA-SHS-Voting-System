package io.github.glavin.votingsystem

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.github.glavin.votingsystem.core.data.local.createDataStore
import io.github.glavin.votingsystem.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App(
    dynamicColor = false
) }