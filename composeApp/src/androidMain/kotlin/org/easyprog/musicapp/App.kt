package org.easyprog.musicapp

import android.app.Application
import data.di.commonModule
import data.di.ktorModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(commonModule, ktorModule)
        }
    }

}