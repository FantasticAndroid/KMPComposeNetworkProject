package com.first.deps

import com.first.network.CensorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    //single { MyRepoImpl(get()) }.bind<MyRepo>()
    viewModelOf(::CensorViewModel)
}

//expect val platformModule: Module

