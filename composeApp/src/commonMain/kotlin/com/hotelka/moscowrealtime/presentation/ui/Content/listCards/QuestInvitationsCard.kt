package com.hotelka.moscowrealtime.presentation.ui.Content.listCards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.presentation.state.listState.QuestInvitationsUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.QuestInvitationItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.quest_invitations

@Composable
fun QuestInvitationsCard(
    modifier: Modifier,
    questInvitationsUiState: QuestInvitationsUiState,
    navigateQuest: (Quest) -> Unit,
    acceptQuestInvitation: (QuestInvitation) -> Unit,
    declineQuestInvitation: (QuestInvitation) -> Unit,
    onDismiss: () -> Unit,

    ) {
    DismissableCardFrame(
        modifier = modifier,
        onDismissRequest = onDismiss,
        content = {
            DismissableCardHeader(Res.string.quest_invitations)
            Spacer(Modifier.height(20.dp))
            if (questInvitationsUiState is QuestInvitationsUiState.Success) {
                LazyColumn(Modifier.fillMaxSize()) {
                    itemsIndexed(questInvitationsUiState.invitations) { index, invitation ->
                        QuestInvitationItem(
                            modifier = Modifier.fillMaxWidth(),
                            invitation,
                            onGoQuest = { navigateQuest(invitation.quest) },
                            onDecline = { declineQuestInvitation(invitation.questInvitation) },
                            onAccept = { acceptQuestInvitation(invitation.questInvitation) }
                        )
                    }
                    item {
                        Spacer(Modifier.height(120.dp))
                    }
                }
            }
        }
    )

}