package com.first.deps

import com.first.network.CensorRepository
import com.first.network.CensorUseCase
import com.first.network.CensorViewModel
import com.first.network.ICensorRepository
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    //single { MyRepoImpl(get()) }.bind<MyRepo>()
    single { CensorUseCase(get()) }
    single { CensorRepository() }.bind<ICensorRepository>()
    viewModelOf(::CensorViewModel)
}

//expect val platformModule: Module

