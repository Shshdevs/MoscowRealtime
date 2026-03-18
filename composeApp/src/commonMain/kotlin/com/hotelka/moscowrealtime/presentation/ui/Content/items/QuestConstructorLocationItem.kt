package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.location_included
import moscowrealtime.composeapp.generated.resources.settings_sliders
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestConstructorLocationItem(location: Location, position: Int, modifier: Modifier = Modifier) {
    DefaultAppCard(modifier = modifier, onClick = null) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Badge(
                modifier = Modifier.size(30.dp),
                contentColor = background,
                containerColor = blue
            ) {
                Text(position.toString(), fontSize = 14.sp)
            }

            Box(Modifier.weight(1f)) {
                LocationItem(location, stringResource(Res.string.location_included))
            }
            Icon(
                painterResource(Res.drawable.settings_sliders),
                "Drag",
                tint = secondaryTextColor.copy(0.8f)
            )
        }
    }
}