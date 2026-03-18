package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MiniCardBriefInfo(modifier: Modifier, icon: DrawableResource, info: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(icon),
            "Some info icon",
            tint = secondaryTextColor
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = info,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = secondaryTextColor,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}