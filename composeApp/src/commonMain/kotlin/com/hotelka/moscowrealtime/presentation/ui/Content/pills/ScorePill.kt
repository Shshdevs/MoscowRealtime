package com.hotelka.moscowrealtime.presentation.ui.Content.pills

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.gold
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.star_filled
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScorePill(modifier: Modifier, score: Int, onClick:() -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = background
        ),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(Res.drawable.star_filled),
                "Score",
                tint = gold,

                )
            Spacer(Modifier.width(10.dp))
            Text(
                score.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = titleTextColor
            )
        }
    }
}