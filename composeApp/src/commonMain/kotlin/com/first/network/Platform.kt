package com.first.network

import io.ktor.client.engine.HttpClientEngine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getNetworkEngine() : HttpClientEngine

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
// Actual is only required in androidMain module to get Context
expect object AppContext {
    fun get(): Any    // will return platform-specific context
}

/**
 * Need Platform specific implementation to read data store path
 * @return String
 */
expect fun getDataStorePrefPath() : String