package com.hotelka.moscowrealtime.presentation.ui.Content.cards

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.group
import moscowrealtime.composeapp.generated.resources.my_groups
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MyGroupsCard(modifier: Modifier = Modifier, onClick:() -> Unit) {
    DefaultAppCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painterResource(Res.drawable.group),
                "Some icon",
                tint = secondaryTextColor,
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.width(10.dp))

            Text(
                text = stringResource(Res.string.my_groups),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = secondaryTextColor,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

        }

    }
}