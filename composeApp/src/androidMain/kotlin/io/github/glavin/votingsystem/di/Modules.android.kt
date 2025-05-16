package io.github.glavin.votingsystem.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.glavin.votingsystem.core.data.local.AppSettings
import io.github.glavin.votingsystem.core.data.local.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    single<DataStore<Preferences>> { createDataStore(androidContext()) }
}