package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.ooops
import moscowrealtime.composeapp.generated.resources.search
import moscowrealtime.composeapp.generated.resources.try_again
import moscowrealtime.composeapp.generated.resources.unrecognized
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RunningRequestUnrecognizedCard(
    modifier: Modifier,
    visible: Boolean = true,
    onContinueQuest: () -> Unit = {}
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painterResource(Res.drawable.search), "Success",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(blue.copy(0.8f))
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.ooops),
                fontSize = 26.sp,
                color = titleTextColor,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(Res.string.unrecognized),
                fontSize = 18.sp,
                color = secondaryTextColor,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier)
            GradientButton(
                colors = listOf(blue.copy(0.8f), purple),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = onContinueQuest,
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    stringResource(Res.string.try_again),
                    color = background
                )
            }
        }
    }
}