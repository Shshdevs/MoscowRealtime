package com.hotelka.moscowrealtime.core.config

expect object AppConfig {
    val webClientId: String
    val supabaseURL: String
    val supabaseKey: String
    val mapkitApiKey: String
    val defaultPlaceHolder: String
}