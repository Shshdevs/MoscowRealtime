package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor

@Composable
fun UserAchievementsInfo(modifier: Modifier, data: String, label: String, onClick:() -> Unit) {
    DefaultAppCard(modifier, onClick = onClick) {
        Column(
            Modifier.Companion
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Text(
                data,
                fontSize = 16.sp,
                fontWeight = FontWeight.Companion.SemiBold,
                color = titleTextColor,
                textAlign = TextAlign.Center

            )
            Text(
                label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Companion.Normal,
                color = secondaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}