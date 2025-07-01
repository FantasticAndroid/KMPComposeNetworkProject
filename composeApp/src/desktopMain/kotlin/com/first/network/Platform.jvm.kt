package com.first.network

import com.first.datastore.pref.dataStoreFileName
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getNetworkEngine(): HttpClientEngine {
    return OkHttp.create()
}

/**
 * Platform specific implementation to read data store path
 * @return String
 */
actual fun getDataStorePrefPath() : String = dataStoreFileName

/**
 * Dummy Implementation of AppContext.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppContext {
    actual fun get() : Any = Any()
}