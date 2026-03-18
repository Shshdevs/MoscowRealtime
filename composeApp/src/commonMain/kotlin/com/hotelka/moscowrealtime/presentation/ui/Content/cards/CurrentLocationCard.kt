package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.indigo
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_quest
import moscowrealtime.composeapp.generated.resources.question
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CurrentLocationCard(
    modifier: Modifier = Modifier,
    location: Location,
    navigateToInteractiveMaps: () -> Unit
) {
    DefaultAppCard(
        modifier.fillMaxWidth().padding(10.dp),
        onClick = navigateToInteractiveMaps,
        content = {
            Row(Modifier.padding(10.dp)){
                Image(
                    painterResource(Res.drawable.question),
                    "Quest Cover",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colorFilter = ColorFilter.tint(indigo),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .height(80.dp)
                        .padding(vertical = 0.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        location.label,
                        color = titleTextColor.copy(0.8f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    GradientButton(
                        modifier = Modifier,
                        colors = listOf(blue, purple),
                        additionalModifier = Modifier.Companion
                            .shimmerLoading(1500, White.copy(0.3f))
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(horizontal = 10.dp),
                        onClick = navigateToInteractiveMaps,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            stringResource(Res.string.continue_quest),
                            color = background,
                            fontSize = 12.sp
                        )
                    }
                }
            }

        }
    )
}