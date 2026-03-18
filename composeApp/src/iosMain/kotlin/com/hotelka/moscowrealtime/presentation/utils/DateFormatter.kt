package com.hotelka.moscowrealtime.presentation.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.preferredLanguages

actual fun formatLocalizedDate(localDateTime: LocalDateTime): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = "d MMMM"
    val preferredLanguages = NSLocale.preferredLanguages()

    dateFormatter.locale = if (preferredLanguages.any { (it as String).startsWith("ru") }) {
        NSLocale(localeIdentifier = "ru_RU")
    } else {
        NSLocale.currentLocale
    }
    val components = NSDateComponents()
    components.apply {
        year = localDateTime.year.toLong()
        month = localDateTime.month.number.toLong()
        day = localDateTime.day.toLong()
    }

    val date = NSCalendar.currentCalendar.dateFromComponents(components)

    return date?.let { dateFormatter.stringFromDate(it) } ?: ""
}