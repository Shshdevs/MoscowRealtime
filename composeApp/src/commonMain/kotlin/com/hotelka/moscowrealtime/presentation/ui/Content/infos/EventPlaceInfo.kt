package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.api.PlaceDetailed
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.online
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EventPlaceInfo(place: PlaceDetailed?, textSize: TextUnit = 14.sp) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painterResource(Res.drawable.location),
            contentDescription = "Place",
            tint = blue,
            modifier = Modifier.size(textSize.value.dp * 2)
        )
        Text(
            text = place?.title?.replaceFirstChar { it.uppercaseChar() }
                ?: stringResource(Res.string.online),
            style = TextStyle.Default.copy(
                color = titleTextColor,
                fontWeight = FontWeight.Normal,
                fontSize = textSize,
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}