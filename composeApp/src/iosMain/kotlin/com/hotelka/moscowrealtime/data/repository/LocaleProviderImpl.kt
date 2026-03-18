package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.LocaleProvider
import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

actual class LocaleProviderImpl actual constructor() : LocaleProvider {
    actual override fun getLocale(): String {
        val preferred = NSLocale.preferredLanguages.firstOrNull() as? String ?: return "en"
        return preferred.substringBefore("-").substringBefore("_")
    }
}