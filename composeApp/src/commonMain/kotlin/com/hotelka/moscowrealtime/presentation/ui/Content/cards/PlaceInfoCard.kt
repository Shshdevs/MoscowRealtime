package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.api.PlaceDetailed
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.subway
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlaceInfoCard(modifier: Modifier = Modifier, place: PlaceDetailed, onClick:() -> Unit) {
    DefaultAppCard(
        modifier = modifier,
        onClick = onClick
    ){
        Column(Modifier.padding(20.dp)) {
            Text(
                place.title.replaceFirstChar { it.uppercase() },
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = titleTextColor.copy(0.8f)
            )
            Spacer(Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(Res.drawable.location),
                    "Address",
                    tint = secondaryTextColor,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    place.address,
                    fontSize = 14.sp,
                    color = secondaryTextColor
                )
            }


            if (place.subway.isNotEmpty()) {
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(Res.drawable.subway),
                        "Address",
                        tint = secondaryTextColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        place.subway,
                        fontSize = 14.sp,
                        color = secondaryTextColor
                    )

                }
            }
        }
    }
}