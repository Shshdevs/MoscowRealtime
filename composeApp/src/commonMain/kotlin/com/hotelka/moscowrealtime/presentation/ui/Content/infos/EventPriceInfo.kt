package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.free
import moscowrealtime.composeapp.generated.resources.price
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PriceInfo(price: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painterResource(Res.drawable.price),
            contentDescription = "Price",
            tint = blue
        )
        Text(
            text = if (price.isEmpty()) stringResource(Res.string.free)
            else price.replaceFirstChar { it.uppercaseChar() },
            style = TextStyle.Default.copy(
                color = titleTextColor,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}