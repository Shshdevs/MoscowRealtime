package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.PillContainer
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add
import moscowrealtime.composeapp.generated.resources.included
import moscowrealtime.composeapp.generated.resources.location_not_included
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddLocationSearchItem(
    location: Location,
    included: Boolean,
    modifier: Modifier = Modifier,
    onAddLocation: () -> Unit
) {
    DefaultAppCard(modifier = modifier, onClick = null) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LocationItem(
                location = location,
                subText = stringResource(if (included) Res.string.included else Res.string.location_not_included),
                Modifier.weight(1f)
            )
            PillContainer(
                color = if (!included) blue else Gray.copy(0.8f),
                onClick = if (!included) onAddLocation else null,
                paddingValues = PaddingValues(vertical = 5.dp, horizontal = 10.dp)
            ) {
                if (!included) {
                    Icon(
                        painterResource(Res.drawable.add), "Add location",
                        modifier = Modifier.size(10.dp),
                        tint = background
                    )
                    Spacer(Modifier.width(5.dp))
                }
                Text(
                    stringResource(if (!included) Res.string.add else Res.string.included),
                    fontSize = 12.sp,
                    color = background
                )
            }
        }
    }
}