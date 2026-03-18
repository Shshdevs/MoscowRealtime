package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.presentation.state.FriendRequestState
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add_to_friends
import moscowrealtime.composeapp.generated.resources.friend
import moscowrealtime.composeapp.generated.resources.friends
import moscowrealtime.composeapp.generated.resources.incoming_request
import moscowrealtime.composeapp.generated.resources.loading
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import moscowrealtime.composeapp.generated.resources.outcoming_request
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BefriendButton(
    modifier: Modifier,
    userProfileId: String,
    friendRequestState: FriendRequestState,
    onClick: (FriendRequest?) -> Unit,
) {
    GradientButton(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.height(60.dp),
        onClick = {
            if (friendRequestState is FriendRequestState.Success) {
                onClick(friendRequestState.friendRequest)
            } else if (friendRequestState is FriendRequestState.None){
                onClick(null)
            }
        }
    ) {
        Icon(
            painterResource(Res.drawable.friend),
            "Add to Friends",
        )
        Spacer(Modifier.width(5.dp))
        Text(
            when (friendRequestState) {
                is FriendRequestState.Error -> stringResource(Res.string.something_went_wrong)
                FriendRequestState.Loading -> stringResource(Res.string.loading)
                FriendRequestState.None -> stringResource(Res.string.add_to_friends)
                is FriendRequestState.Success -> {
                    val userFrom = friendRequestState.friendRequest.userFrom

                    when (friendRequestState.friendRequest.status) {
                        "friends" -> stringResource(Res.string.friends)
                        else -> {
                            if (userFrom == userProfileId) {
                                stringResource(Res.string.incoming_request)
                            } else {
                                stringResource(Res.string.outcoming_request)
                            }
                        }
                    }
                }
            },
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}