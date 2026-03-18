package com.hotelka.moscowrealtime.presentation.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

actual fun formatLocalizedDate(localDateTime: LocalDateTime): String {
    val calendar = Calendar.getInstance().apply {
        set(localDateTime.year, localDateTime.month.number - 1, localDateTime.day)
    }
    val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
    return dateFormat.format(calendar.time)
}