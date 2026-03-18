package com.hotelka.moscowrealtime.presentation.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.good_evening
import moscowrealtime.composeapp.generated.resources.good_morning
import moscowrealtime.composeapp.generated.resources.good_night
import moscowrealtime.composeapp.generated.resources.hello
import org.jetbrains.compose.resources.getString
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object DateFormatters {
    @OptIn(ExperimentalTime::class)
    fun Long.toDate(
        includeTime: Boolean = false
    ): String {
        val localDateTime = Instant.fromEpochSeconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val dateString = "${localDateTime.day.toString().padStart(2, '0')}." +
                "${localDateTime.month.number.toString().padStart(2, '0')}." +
                "${localDateTime.year}"

        return if (includeTime) {
            val timeString = "${localDateTime.hour.toString().padStart(2, '0')}:" +
                    localDateTime.minute.toString().padStart(2, '0')
            "$dateString $timeString"
        } else {
            dateString
        }
    }
    @OptIn(ExperimentalTime::class)
    suspend fun getGreeting(): String {
        val currentMoment = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()
        val localDateTime = currentMoment.toLocalDateTime(timeZone)
        val hour = localDateTime.hour

        val dateString = formatLocalizedDate(localDateTime)

        val greetingResource = when (hour) {
            in 5..11 -> Res.string.good_morning
            in 12..17 -> Res.string.hello
            in 22 .. 3 -> Res.string.good_night
            else -> Res.string.good_evening
        }

        return getString(greetingResource) + dateString
    }
}
expect fun formatLocalizedDate(localDateTime: LocalDateTime): String

