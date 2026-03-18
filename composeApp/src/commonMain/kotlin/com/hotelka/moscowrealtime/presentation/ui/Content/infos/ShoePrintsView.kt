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
import com.hotelka.moscowrealtime.presentation.extensions.formatDistance
import com.hotelka.moscowrealtime.presentation.ui.Custom.LoadingText
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.shoe_prints
import org.jetbrains.compose.resources.painterResource

@Composable
fun ShoePrintsView(distance: Float?) {
    Icon(
        painterResource(Res.drawable.shoe_prints),
        "Distance",
        modifier = Modifier.size(24.dp),
        tint = blue
    )
    Spacer(
        Modifier.width(5.dp)
    )
    if (distance == 0f) {
        LoadingText(
            Modifier.width(80.dp),
            textSize = 14.sp,
            color = blue,
        )
    } else {
        distance?.formatDistance()?.let {
            Text(
                it,
                color = secondaryTextColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
    }

}