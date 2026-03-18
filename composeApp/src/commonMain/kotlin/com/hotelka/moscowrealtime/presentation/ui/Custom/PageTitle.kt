package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor

@Composable
fun PageTitle(title: String) {
    Text(
        title.replaceFirstChar { it.uppercaseChar() },
        style = TextStyle.Default.copy(
            color = titleTextColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
        )
    )
}