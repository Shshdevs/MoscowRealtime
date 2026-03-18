package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SearchUsersForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.InviteUserItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add_participant
import moscowrealtime.composeapp.generated.resources.add_participants

@Composable
fun AddParticipantsCard(
    visible: Boolean,
    searchParticipantQuery: String,
    searchParticipantsListState: UsersListState,
    group: Group,
    addParticipant: (User) -> Unit,
    searchUsers: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    SlideVerticallyCardAnim(
        modifier = Modifier.fillMaxSize().padding(top = 120.dp),
        visible = visible,
    ) {
        DismissableCardFrame(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = onDismiss
        ) {
            DismissableCardHeader(Res.string.add_participants)
            Spacer(Modifier.height(20.dp))
            SearchUsersForm(
                modifier = Modifier.fillMaxSize(),
                finalQuery = searchParticipantQuery,
                usersListState = searchParticipantsListState,
                userSearchItem = { user ->
                    InviteUserItem(
                        modifier = Modifier.fillMaxWidth(),
                        inviteAction = Res.string.add_participant,
                        user = user,
                        onClick = {
                            if (user.id !in group.users) addParticipant(user)
                        },
                    )
                },
                searchUsers = searchUsers,
            )
        }
    }
}