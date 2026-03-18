package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.LocaleProvider
import com.hotelka.moscowrealtime.AndroidContextHolder

actual class LocaleProviderImpl actual constructor(): LocaleProvider {
    actual override fun getLocale(): String{
        val config = AndroidContextHolder.applicationContext.resources.configuration
        return config.locales.get(0).language
    }
}