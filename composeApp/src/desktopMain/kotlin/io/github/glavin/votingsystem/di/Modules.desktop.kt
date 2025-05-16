package io.github.glavin.votingsystem.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.glavin.votingsystem.core.data.local.createDataStore
import io.github.glavin.votingsystem.core.domain.BuildStage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
    single<DataStore<Preferences>> {
        createDataStore(BuildStage.TESTING)
    }
}