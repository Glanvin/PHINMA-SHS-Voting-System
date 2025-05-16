package io.github.glavin.votingsystem

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.singleWindowApplication
import io.github.glavin.votingsystem.di.initKoin

fun main() {

    initKoin()

//    application {
//        Window(
//            onCloseRequest = ::exitApplication,
//            title = "Chiro",
//        ) {
//            singleWindowApplication {
//                App()
//            }
//        }
//    }

    singleWindowApplication {
        App()
    }
}