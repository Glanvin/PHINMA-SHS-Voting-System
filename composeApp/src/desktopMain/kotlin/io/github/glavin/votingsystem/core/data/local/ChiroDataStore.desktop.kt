package io.github.glavin.votingsystem.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.glavin.votingsystem.core.domain.BuildStage
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

actual fun createDataStore(context: Any?): DataStore<Preferences> {

    require(context is BuildStage)

    val path = when(context) {
        BuildStage.DEVELOPMENT -> {
            val tempDir = System.getProperty("java.io.tempdir")
            Paths.get(tempDir, SETTINGS_PREFERENCES_FILE_NAME)
        }
        else -> {
            val appData = System.getenv("APPDATA") ?: (System.getProperty("user.home") + "/.config")
            val appDir = Paths.get(appData, SETTINGS_PREFERENCES_FILE_NAME)
            appDir.createDirectories()
            appDir.resolve(SETTINGS_PREFERENCES_FILE_NAME).toAbsolutePath()
        }
    }

    return AppSettings.getDataStore {
        path.toString()
    }
}