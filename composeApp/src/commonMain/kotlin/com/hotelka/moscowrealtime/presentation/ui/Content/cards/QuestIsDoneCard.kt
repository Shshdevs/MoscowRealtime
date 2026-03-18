package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ShimmeringButton
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.FazingCover
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.quest_is_done
import moscowrealtime.composeapp.generated.resources.restart_quest
import moscowrealtime.composeapp.generated.resources.view_my_results
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestIsDoneCard(
    visible: Boolean,
    quest: Quest,
    onDismiss: () -> Unit,
    onRestartQuest: () -> Unit
) {
    SlideVerticallyCardAnim(
        modifier = Modifier.fillMaxSize(),
        visible = visible,
    ) {
        DismissableCardFrame(
            Modifier.fillMaxSize(),
            dismissAvailable = false,
            onDismissRequest = {}
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FazingCover(
                    quest.cover,
                    modifier = Modifier.size(200.dp)
                )

                Spacer(Modifier.height(20.dp))
                Text(
                    text = stringResource(Res.string.quest_is_done),
                    fontSize = 26.sp,
                    color = titleTextColor,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = quest.title,
                    fontSize = 18.sp,
                    color = secondaryTextColor,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier)
                ShimmeringButton(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = Res.string.view_my_results,
                    onClick = onDismiss,
                )
                OutlinedGradientButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = onRestartQuest,
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text(stringResource(Res.string.restart_quest))
                }
            }
        }
    }
}