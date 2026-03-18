package com.hotelka.moscowrealtime.presentation.ui.theme

import androidx.compose.ui.graphics.Color

val background = "#f6fafd".toColor()
val darkBlue = "#0a1931".toColor()
val indigo = "#1a3d63".toColor()
val titleTextColor = "#181a1f".toColor()
val secondaryTextColor = "#565d6d".toColor()
val blue = "#0074f0".toColor()
val errorColor = "#a7251f".toColor()
val purple = "#7c3aed".toColor()
val gold = "#f5d015".toColor()
val silver = "#c0c0c0".toColor()
val bronze = "#CD7F32".toColor()
val red = "#cf203d".toColor()
fun String.toColor(): Color {
    var hex = this.trim().uppercase()

    if (hex.startsWith("#")) {
        hex = hex.substring(1)
    }

    return when (hex.length) {
        6 -> {
            val r = hex.substring(0, 2).toInt(16) / 255f
            val g = hex.substring(2, 4).toInt(16) / 255f
            val b = hex.substring(4, 6).toInt(16) / 255f
            Color(r, g, b)
        }
        8 -> {
            val a = hex.substring(0, 2).toInt(16) / 255f
            val r = hex.substring(2, 4).toInt(16) / 255f
            val g = hex.substring(4, 6).toInt(16) / 255f
            val b = hex.substring(6, 8).toInt(16) / 255f
            Color(r, g, b, a)
        }
        else -> throw IllegalArgumentException("Invalid hex color: $this")
    }
}
