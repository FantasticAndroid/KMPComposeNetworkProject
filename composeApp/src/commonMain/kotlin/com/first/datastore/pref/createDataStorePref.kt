package com.first.datastore.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.first.network.getDataStorePrefPath
import okio.Path.Companion.toPath

/**
 * Common method or code to Create Data Store. This method requires produceFile which is a dataStore file path which is platform specific.
 * Therefore, getDataStorePrefPath() is a platform specific method and have expect/actual implementations.
 */
fun createDataStorePref() : DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(produceFile = { getDataStorePrefPath().toPath() })

internal const val dataStoreFileName = "censor.preferences_pb"
