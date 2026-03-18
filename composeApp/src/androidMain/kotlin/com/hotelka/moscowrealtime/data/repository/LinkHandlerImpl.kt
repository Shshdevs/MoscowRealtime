package com.hotelka.moscowrealtime.data.repository

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.hotelka.moscowrealtime.domain.repository.LinkHandler
import com.hotelka.moscowrealtime.AndroidContextHolder

actual class LinkHandlerImpl actual constructor(): LinkHandler {
    actual override fun openUrl(url: String) {
        val context = AndroidContextHolder.applicationContext
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }
}