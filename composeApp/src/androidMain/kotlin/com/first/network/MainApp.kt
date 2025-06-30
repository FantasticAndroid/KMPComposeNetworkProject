package com.first.network

import android.app.Application
import com.first.deps.initKoin
import org.koin.android.ext.koin.androidContext

class MainApp : Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MainApp)
        }
    }
}