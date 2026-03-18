package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.LinkHandler
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class LinkHandlerImpl : LinkHandler {
    actual override fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url)
        nsUrl?.let {
            UIApplication.sharedApplication.openURL(
                it, options = mapOf<Any?, String>(),
                completionHandler = {})
        }
    }
}