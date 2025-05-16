package io.github.glavin.votingsystem.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    require(
        value = context is Context,
        lazyMessage = { "Parameter 'context' must be an instance of 'Context'." }
    )

    return AppSettings.getDataStore {
        context.filesDir.resolve(SETTINGS_PREFERENCES_FILE_NAME).absolutePath
    }

}