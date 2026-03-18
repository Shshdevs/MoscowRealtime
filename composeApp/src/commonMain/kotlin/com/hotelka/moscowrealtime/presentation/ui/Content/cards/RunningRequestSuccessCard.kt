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
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ShimmeringButton
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.FazingCover
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_quest
import moscowrealtime.composeapp.generated.resources.success
import moscowrealtime.composeapp.generated.resources.view_result
import org.jetbrains.compose.resources.stringResource

@Composable
fun RunningRequestSuccessCard(
    modifier: Modifier,
    visible: Boolean,
    location: Location,
    onShowContinueToResult: () -> Unit = {},
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
            FazingCover(
                location.picture.toString(),
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.success),
                fontSize = 26.sp,
                color = titleTextColor,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = location.label,
                fontSize = 18.sp,
                color = secondaryTextColor,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier)
            ShimmeringButton(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = Res.string.continue_quest,
                onClick = onContinueQuest,
            )
            OutlinedGradientButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = onShowContinueToResult,
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(stringResource(Res.string.view_result))
            }
        }
    }
}