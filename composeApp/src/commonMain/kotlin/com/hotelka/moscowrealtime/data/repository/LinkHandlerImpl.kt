package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.repository.LinkHandler

expect class LinkHandlerImpl(): LinkHandler {
    override fun openUrl(url: String)
}