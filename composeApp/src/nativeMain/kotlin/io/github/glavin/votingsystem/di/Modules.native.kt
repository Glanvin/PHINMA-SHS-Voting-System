package io.github.glavin.votingsystem.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.glavin.votingsystem.core.data.local.createDataStore
import org.koin.dsl.module

actual val platformModule = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
}