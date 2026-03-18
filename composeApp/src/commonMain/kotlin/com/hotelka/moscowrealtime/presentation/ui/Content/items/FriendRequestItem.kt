package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.ExpandableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.CardActionsButtons
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.FriendRequestHeader
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.accept
import moscowrealtime.composeapp.generated.resources.decline
import moscowrealtime.composeapp.generated.resources.view_profile

@Composable
fun FriendRequestItem(modifier: Modifier, user: User, onAccept: () -> Unit, onDecline: () -> Unit, onViewProfile: () -> Unit) {

    ExpandableCardFrame(
        modifier = modifier.fillMaxWidth(),
        topContent = {
            FriendRequestHeader(user)
        },
        bottomContent = {
            CardActionsButtons(
                mainActionText = Res.string.accept,
                secondaryActionText = Res.string.decline,
                belowActionText = Res.string.view_profile,
                mainAction = onAccept,
                secondaryAction = onDecline,
                belowAction = onViewProfile
            )
        }
    )
}