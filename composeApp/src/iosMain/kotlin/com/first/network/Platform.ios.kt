package com.first.network

import com.first.datastore.pref.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIDevice
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

/**
 * Platform specific implementation to read data store path
 * @return String
 */
@OptIn(ExperimentalForeignApi::class)
actual fun getDataStorePrefPath(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path + "/$dataStoreFileName"
}

actual fun SettingsLauncher.Handle() {
    if (requestSettings) {
        val settingsUrl = NSURL.URLWithString(URLString = UIApplicationOpenSettingsURLString)

        if (settingsUrl != null && UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
            UIApplication.sharedApplication.openURL(settingsUrl)
        }
        requestSettings = false
    }
}