package com.hotelka.moscowrealtime.presentation.extensions

import com.hotelka.moscowrealtime.domain.model.api.EventDate
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun List<EventDate>.getTheNearestDate(): EventDate? {
    val currentTimestamp = Clock.System.now().epochSeconds

    return this.filter { it.start >= currentTimestamp }
        .minByOrNull { it.start - currentTimestamp }
}

fun <T> MutableList<T>.move(from: Int, to: Int) {
    if (from == to) return
    val item = removeAt(from)
    add(to, item)
}