package com.first.deps

import com.first.datastore.pref.createDataStorePref
import com.first.network.CensorRepository
import com.first.network.CensorUseCase
import com.first.network.CensorViewModel
import com.first.network.ICensorRepository
import com.first.network.client.createHttpClient
import com.first.network.getNetworkEngine
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { createHttpClient(getNetworkEngine()) }
    single { CensorRepository(get()) }.bind<ICensorRepository>()
    single { CensorUseCase(get()) }
    viewModelOf(::CensorViewModel)
    // Make DataStore<preference> Injectable
    single { createDataStorePref() }
}

//expect val platformModule: Module

