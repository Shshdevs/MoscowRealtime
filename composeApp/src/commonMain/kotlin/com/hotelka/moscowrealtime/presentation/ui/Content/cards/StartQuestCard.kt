package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ShimmeringButton
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SearchWithMenu
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.QuestInvitationHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.UserItem
import com.hotelka.moscowrealtime.presentation.ui.Custom.SwipeToDeleteBox
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.invite_friends
import moscowrealtime.composeapp.generated.resources.invited
import moscowrealtime.composeapp.generated.resources.start_continue_quest
import moscowrealtime.composeapp.generated.resources.start_quest_invitations
import org.jetbrains.compose.resources.stringResource

@Composable
fun StartQuestCard(
    modifier: Modifier,
    quest: Quest,
    friends: UsersListState,
    onSearch:(String) -> Unit,
    onStart: (List<String>) -> Unit,
    onDismiss: () -> Unit,
) {
    val friendsInvited = remember { mutableStateListOf<User>() }
    DismissableCardFrame(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        Column(
            Modifier.animateContentSize().padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DismissableCardHeader(Res.string.start_quest_invitations)
            QuestInvitationHeader(Modifier, quest)

            Column {
                Text(
                    stringResource(Res.string.invite_friends),
                    color = titleTextColor.copy(0.8f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(10.dp))
                SearchWithMenu(
                    filterContent = onSearch,
                    menuContents = {
                        if (friends is UsersListState.Success) {
                            friends.users.forEach { user ->
                                UserItem(
                                    Modifier.fillMaxWidth().padding(10.dp)
                                        .background(
                                            if (friendsInvited.map { it.id }.contains(user.id))
                                                blue.copy(0.1f)
                                            else background, RoundedCornerShape(20.dp)
                                        ),
                                    user = user,
                                    onClick = {
                                        if (!friendsInvited.map { it.id }
                                                .contains(user.id)) friendsInvited.add(user)
                                    }
                                )
                            }
                        }
                    }
                )
            }

            if (friendsInvited.isNotEmpty()) {
                Column {
                    Text(
                        stringResource(Res.string.invited),
                        color = titleTextColor.copy(0.8f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(10.dp))
                    LazyColumn {
                        items(friendsInvited) { user ->
                            SwipeToDeleteBox(
                                key = user.id,
                                onDelete = { friendsInvited.remove(user) },
                                content = {
                                    UserItem(
                                        Modifier.fillMaxWidth().background(
                                            background,
                                            RoundedCornerShape(15.dp)
                                        ), user
                                    ) {}
                                }
                            )
                        }
                    }
                }
            }
            ShimmeringButton(
                Res.string.start_continue_quest,
                { onStart(friendsInvited.map { it.id }) })
        }
    }
}