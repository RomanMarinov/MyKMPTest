package com.dev_marinov.my_compose_multi.ui

//import org.koin.core.context.startKoin

import android.app.Application
import di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

// koin documentation
//https://insert-koin.io/docs/reference/koin-android/start

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // помогает отслеживать и понимать, что происходит во время инициализации и работы Koin в вашем Android-приложении
            androidLogger()
            // используется для доступа к ресурсам приложения, управления жизненным циклом и других операций, которые требуют доступа к контексту Android
            androidContext(this@MainApplication)
            modules(commonModule(), )
        }
    }
}