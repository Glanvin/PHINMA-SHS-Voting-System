@file:OptIn(ExperimentalForeignApi::class)

package io.github.glavin.votingsystem.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(context: Any?): DataStore<Preferences> {
    return AppSettings.getDataStore(
        producePath = {
            val  directory: NSURL? = NSFileManager
                .defaultManager
                .URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null
                )
            requireNotNull(directory).path + "/$SETTINGS_PREFERENCES_FILE_NAME"
        }
    )
}