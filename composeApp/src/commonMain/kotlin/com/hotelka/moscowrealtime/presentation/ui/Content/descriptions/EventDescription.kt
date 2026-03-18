package com.hotelka.moscowrealtime.presentation.ui.Content.descriptions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.extensions.removeHTMLTags
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor

@Composable
fun EventDescription(
    description: String
) {
    Text(
        description.removeHTMLTags().trim(),
        style = TextStyle.Default.copy(
            color = secondaryTextColor,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        )
    )
}