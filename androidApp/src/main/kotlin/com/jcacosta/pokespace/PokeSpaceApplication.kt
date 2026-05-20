package com.jcacosta.pokespace

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokeSpaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokeSpaceApplication)
            modules(appModule)
        }
    }
}