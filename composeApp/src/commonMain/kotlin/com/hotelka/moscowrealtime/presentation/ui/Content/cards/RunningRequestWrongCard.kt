package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.FazingCover
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_quest
import moscowrealtime.composeapp.generated.resources.thats_the
import moscowrealtime.composeapp.generated.resources.view_result
import moscowrealtime.composeapp.generated.resources.wrong
import moscowrealtime.composeapp.generated.resources.you_can_still
import org.jetbrains.compose.resources.stringResource

@Composable
fun RunningRequestWrongCard(
    modifier: Modifier,
    visible: Boolean,
    location: Location,
    onShowContinueToResult: () -> Unit,
    onContinueQuest: () -> Unit
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
            FazingCover(
                location.picture.toString(),
                direction = -1,
                modifier = Modifier.size(200.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.wrong),
                fontSize = 26.sp,
                color = titleTextColor,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(Res.string.thats_the) + location.label + ". " + stringResource(
                    Res.string.you_can_still
                ),
                fontSize = 18.sp,
                color = secondaryTextColor,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            GradientButton(
                colors = listOf(blue.copy(0.8f), purple),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = onContinueQuest,
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    stringResource(Res.string.continue_quest)
                )
            }

            OutlinedGradientButton(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(15.dp),
                onClick = onShowContinueToResult,
                content = {
                    Text(stringResource(Res.string.view_result))
                }
            )
        }
    }
}