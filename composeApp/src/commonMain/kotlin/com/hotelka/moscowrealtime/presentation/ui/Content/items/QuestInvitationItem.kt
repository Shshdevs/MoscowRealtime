package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.detailed.QuestInvitationDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.ExpandableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.CardActionsButtons
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.QuestInvitationCardHeader
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.accept
import moscowrealtime.composeapp.generated.resources.decline
import moscowrealtime.composeapp.generated.resources.view_the_quest

@Composable
fun QuestInvitationItem(
    modifier: Modifier,
    questInvitationDetailed: QuestInvitationDetailed,
    onGoQuest: () -> Unit,
    onDecline: () -> Unit,
    onAccept: () -> Unit
) {
    ExpandableCardFrame(
        modifier = modifier,
        showHorizDiver = false,
        topContent = {
            QuestInvitationCardHeader(
                quest = questInvitationDetailed.quest,
                user = questInvitationDetailed.userFrom
            )
        },
        bottomContent = {
            CardActionsButtons(
                mainActionText = Res.string.accept,
                secondaryActionText = Res.string.decline,
                belowActionText = Res.string.view_the_quest,
                mainAction = onAccept,
                secondaryAction = onDecline,
                belowAction = onGoQuest
            )
        }
    )
}