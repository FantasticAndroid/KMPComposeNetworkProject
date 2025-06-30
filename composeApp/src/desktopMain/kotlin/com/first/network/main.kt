package com.first.network

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.first.deps.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "FirstNetworkProject",
        ) {
            App()
        }
    }
}