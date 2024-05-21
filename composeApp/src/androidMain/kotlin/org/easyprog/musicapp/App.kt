package org.easyprog.musicapp

import android.app.Application
import di.mediaControllerModule
import di.ktorModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(mediaControllerModule, ktorModule, repositoryModule, viewModelModule, useCaseModule)
        }
    }

}