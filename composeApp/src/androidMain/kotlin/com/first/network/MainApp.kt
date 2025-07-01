package com.first.network

import android.app.Application
import com.first.deps.initKoin
import org.koin.android.ext.koin.androidContext

class MainApp : Application(){

    override fun onCreate() {
        super.onCreate()
        // Init AppContext and bind Context in it (Statically)
        AppContext.init(this.applicationContext)
        initKoin{
            androidContext(this@MainApp)
        }
    }
}