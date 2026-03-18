package com.hotelka.moscowrealtime.presentation.ui.Content.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.state.FriendsRequestsUiState
import com.hotelka.moscowrealtime.presentation.state.listState.QuestInvitationsUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.UserAchievementsInfo
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.friends
import moscowrealtime.composeapp.generated.resources.have_quest_invitations
import moscowrealtime.composeapp.generated.resources.incoming_requests
import moscowrealtime.composeapp.generated.resources.quest_invitations
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserAchievements(
    user: User,
    friendRequests: FriendsRequestsUiState,
    questInvitations: QuestInvitationsUiState,
    openFriends: () -> Unit,
    openFriendRequests: () -> Unit,
    openQuestInvitations: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        UserAchievementsInfo(
            Modifier.weight(1f),
            user.friends.size.toString(),
            stringResource(Res.string.friends),
            openFriends
        )
        UserAchievementsInfo(
            Modifier.weight(1f),
            if (friendRequests is FriendsRequestsUiState.Success) friendRequests.friendRequests.size.toString() else "0",
            stringResource(Res.string.incoming_requests),
            openFriendRequests
        )
    }
    if (questInvitations is QuestInvitationsUiState.Success && questInvitations.invitations.isNotEmpty()) {
        UserAchievementsInfo(
            Modifier.fillMaxWidth(),
            stringResource(Res.string.quest_invitations),
            stringResource(Res.string.have_quest_invitations),
            openQuestInvitations
        )
    }
}