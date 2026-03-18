package com.hotelka.moscowrealtime.presentation.ui.Content.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.state.FriendRequestState
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.BefriendButton
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.UserAchievementsInfo
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.friends
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserProfileActions(
    user: User,
    friendRequestState: FriendRequestState,
    onDeleteFriend: () -> Unit,
    sendFriendRequest: () -> Unit,
    acceptFriendRequest: (FriendRequest) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        UserAchievementsInfo(
            Modifier.weight(1f),
            user.friends.size.toString(),
            stringResource(Res.string.friends)
        ) {}
        BefriendButton(
            modifier = Modifier.weight(1f),
            userProfileId = user.id,
            friendRequestState = friendRequestState,
            onClick = { friendRequest ->
                if (friendRequest == null) {
                    sendFriendRequest()
                } else {
                    when (friendRequest.status) {
                        "friends" -> {
                            onDeleteFriend()
                        }
                        else -> {
                            if (friendRequest.userFrom == user.id) {
                                acceptFriendRequest(friendRequest)
                            }
                        }

                    }
                }
            })
    }
}