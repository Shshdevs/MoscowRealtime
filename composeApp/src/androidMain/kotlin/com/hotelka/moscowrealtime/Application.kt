package com.hotelka.moscowrealtime

import android.app.Application
import android.content.Context
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.core.config.AppConfig
import com.hotelka.moscowrealtime.di.appModule
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MoscowRTApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidContextHolder.applicationContext = applicationContext

        startKoin {
            androidContext(this@MoscowRTApplication)
            modules(appModule)
        }

        NotifierManager.initialize(
            NotificationPlatformConfiguration.Android(
                android.R.drawable.stat_notify_chat,
                showPushNotification = true
            )
        )

        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onPushNotificationWithPayloadData(
                title: String?,
                body: String?,
                data: PayloadData
            ) {
                Logger.withTag("PushPayload").d { "title=$title body=$body data=$data" }
            }

            override fun onNotificationClicked(data: PayloadData) {
                Logger.withTag("PushPayload").d { "clicked data=$data" }
            }
        })

        MapKitFactory.setApiKey(AppConfig.mapkitApiKey)
        MapKitFactory.initialize(this)
    }
}

object AndroidContextHolder {
    lateinit var applicationContext: Context
}

