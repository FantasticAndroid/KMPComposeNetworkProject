package com.first.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.first.datastore.pref.dataStoreFileName
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp


class AndroidPlatform : Platform {
    override val name: String = "Android SDK: ${Build.VERSION.SDK_INT}, OS: ${Build.VERSION.RELEASE}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getNetworkEngine(): HttpClientEngine {
    return OkHttp.create()
}

/**
 * Platform specific class to set and get Context in case of Android.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@SuppressLint("StaticFieldLeak")
actual object AppContext {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    actual fun get() : Any = context

}

/**
 * Platform specific implementation to read data store path
 * @return String
 */
actual fun getDataStorePrefPath(): String = (AppContext.get() as Context).filesDir.resolve(dataStoreFileName).absolutePath

actual fun SettingsLauncher.Handle() {
    if (requestSettings) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", (AppContext.get() as Context).packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        (AppContext.get() as Context).startActivity(intent)
        requestSettings = false
    }
}