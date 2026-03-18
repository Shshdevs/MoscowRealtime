package com.hotelka.moscowrealtime.core.config

import platform.Foundation.NSBundle

actual object AppConfig {
    actual val webClientId: String = NSBundle.mainBundle.objectForInfoDictionaryKey("GIDClientID") as String
    actual val supabaseURL: String = NSBundle.mainBundle.objectForInfoDictionaryKey("SUPABASE_URL") as String
    actual val supabaseKey: String = NSBundle.mainBundle.objectForInfoDictionaryKey("SUPABASE_KEY") as String
    actual val mapkitApiKey: String = ""
    actual val defaultPlaceHolder: String = NSBundle.mainBundle.objectForInfoDictionaryKey("default_placeholder") as String

}