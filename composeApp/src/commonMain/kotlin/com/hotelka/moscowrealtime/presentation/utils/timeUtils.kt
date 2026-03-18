package com.hotelka.moscowrealtime.presentation.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getCurrentWeekRange(): Pair<Long, Long> {
    val now = Clock.System.now()
    val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val monday = when (today.dayOfWeek) {
        DayOfWeek.MONDAY -> today
        else -> {
            val daysToSubtract = today.dayOfWeek.isoDayNumber - DayOfWeek.MONDAY.isoDayNumber
            today.minus(daysToSubtract, DateTimeUnit.DAY)
        }
    }
    val sunday = monday.plus(6, DateTimeUnit.DAY)
    val mondayStart = LocalDateTime(monday.year, monday.month, monday.day, 0, 0)
    val sundayEnd = LocalDateTime(sunday.year, sunday.month, sunday.day, 23, 59, 59, 999_999_999)

    return Pair(
        mondayStart.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
        sundayEnd.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    )
}


@OptIn(ExperimentalTime::class)
fun currentWeekOfYear(): Int {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.UTC)
        .date

    val firstDay = LocalDate(today.year, 1, 1)
    val dayOfYear = firstDay.daysUntil(today) + 1

    return ((dayOfYear - 1) / 7) + 1
}

@OptIn(ExperimentalTime::class)
fun currentTimestamp(): Long {
    return Clock.System.now().toEpochMilliseconds()
}