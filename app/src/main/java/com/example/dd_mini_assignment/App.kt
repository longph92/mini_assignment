package com.example.dd_mini_assignment

import android.app.Application
import com.example.dd_mini_assignment.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                apiModule,
                viewModelModule,
                repositoryModule,
                useCaseModule
            )
        }
    }
}