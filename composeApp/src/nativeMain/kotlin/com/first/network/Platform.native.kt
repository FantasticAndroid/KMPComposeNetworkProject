package com.first.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

/**
 * In Native main and not in iosMain because Darwin in native impl for Ktor
 * that support iOS as well.
 * @return HttpClientEngine
 */
actual fun getNetworkEngine(): HttpClientEngine {
    return Darwin.create()
}

/**
 * Dummy Implementation of AppContext.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppContext {
    actual fun get() : Any = Any()
}