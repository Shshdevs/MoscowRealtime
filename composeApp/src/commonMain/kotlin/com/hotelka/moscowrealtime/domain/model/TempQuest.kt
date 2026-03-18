package com.hotelka.moscowrealtime.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class TempQuest(
    val title: String = "",
    val description: String = "",
    val cover: ImageBitmap? = null,
    val locations: List<Location> = emptyList(),
    val tags: List<String> = emptyList()
)