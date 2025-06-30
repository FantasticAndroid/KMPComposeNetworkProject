package com.first.network

import androidx.compose.ui.window.ComposeUIViewController
import com.first.deps.initKoin

fun MainViewController() = ComposeUIViewController(configure = {
    initKoin()
}) { App() }