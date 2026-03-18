package com.hotelka.moscowrealtime.data.remote.constants

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object KudaGoDefaults {
    @OptIn(ExperimentalTime::class)
    val defaultActualSince = Clock.System.now().epochSeconds
    val defaultOrderBy = "-publication_date"
    val defaultLocation = "msk"
}