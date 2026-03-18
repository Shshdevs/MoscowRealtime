package com.hotelka.moscowrealtime.presentation.ui.Content.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add_participants
import moscowrealtime.composeapp.generated.resources.pin_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun GroupPageActionsCard(
    modifier: Modifier,
    onInviteParticipant: () -> Unit,
    onPinQuest: () -> Unit
) {
    DefaultAppCard(
        modifier = modifier
            .offset(y = 40.dp),
        onClick = null
    ) {
        Row(Modifier.padding(10.dp)) {
            OutlinedGradientButton(
                modifier = Modifier.weight(1f),
                onClick = onInviteParticipant,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    stringResource(Res.string.add_participants),
                )
            }
            Spacer(Modifier.width(10.dp))
            GradientButton(
                modifier = Modifier.weight(1f),
                onClick = onPinQuest,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(Res.string.pin_quest))
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}