package com.hotelka.moscowrealtime.di

import org.koin.dsl.module

val appModule = module {
    includes(coreModule, repositoryModule, useCasesModule, uiModule)
}