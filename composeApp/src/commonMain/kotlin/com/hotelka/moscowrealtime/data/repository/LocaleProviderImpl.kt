package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.LocaleProvider

expect class LocaleProviderImpl(): LocaleProvider {
    override fun getLocale(): String
}