package com.hotelka.moscowrealtime.di

import com.hotelka.moscowrealtime.data.repository.GeoLocationHandlerImpl
import com.hotelka.moscowrealtime.data.repository.LinkHandlerImpl
import com.hotelka.moscowrealtime.data.repository.LocaleProviderImpl
import com.hotelka.moscowrealtime.data.repository.NetworkMonitorImpl
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import com.hotelka.moscowrealtime.domain.repository.LinkHandler
import com.hotelka.moscowrealtime.domain.repository.LocaleProvider
import com.hotelka.moscowrealtime.domain.repository.NetworkMonitor
import com.hotelka.moscowrealtime.core.config.AppConfig
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreModule = module {
    single<GeoLocationHandler> { GeoLocationHandlerImpl() }
    single<LinkHandler> { LinkHandlerImpl() }
    single<LocaleProvider> { LocaleProviderImpl() }
    single<NetworkMonitor> { NetworkMonitorImpl() }

    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    single<SupabaseClient> {
        createSupabaseClient(
            AppConfig.supabaseURL,
            supabaseKey = AppConfig.supabaseKey
        ) { install(Storage) }
    }

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }

            defaultRequest {
                headersOf("ngrok-skip-browser-warning", "any")
                contentType(ContentType.Application.Json)
            }
        }
    }
}
