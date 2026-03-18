package com.hotelka.moscowrealtime.core.config

import com.hotelka.moscowrealtime.BuildConfig

actual object AppConfig {
    actual val webClientId: String = BuildConfig.WEB_CLIENT_ID
    actual val supabaseKey: String = BuildConfig.SUPABASE_KEY
    actual val supabaseURL: String = BuildConfig.SUPABASE_URL
    actual val mapkitApiKey: String = BuildConfig.MAPKIT_API_KEY
    actual val defaultPlaceHolder: String = BuildConfig.DEFAULT_PLACEHOLDER
}