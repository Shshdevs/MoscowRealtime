package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.extensions.formatDuration
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.Custom.LoadingText
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.duration
import org.jetbrains.compose.resources.painterResource

@Composable
fun DurationView(duration: Float?) {
    Icon(
        painterResource(Res.drawable.duration),
        "Distance",
        modifier = Modifier.size(24.dp),
        tint = blue
    )
    Spacer(Modifier.width(5.dp))
    if (duration == 0f){
        LoadingText(
            Modifier.width(80.dp),
            textSize = 14.sp,
            color = blue,
        )
    } else {
        duration?.formatDuration()?.let {
            Text(
                it,
                color = secondaryTextColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}