package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

sealed class RequestState {
    object None: RequestState()
    object Running: RequestState()
    object Unrecognized: RequestState()
    data class Error(val error: String): RequestState()
    data class Recognized(val discover: DiscoverDetailed): RequestState()
}