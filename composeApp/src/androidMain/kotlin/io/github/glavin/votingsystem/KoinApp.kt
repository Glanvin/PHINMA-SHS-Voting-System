package io.github.glavin.votingsystem

import android.app.Application
import io.github.glavin.votingsystem.di.initKoin
import io.github.glavin.votingsystem.di.platformModule
import org.koin.android.ext.koin.androidContext

class KoinApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KoinApp)
            modules(platformModule)
        }
    }
}