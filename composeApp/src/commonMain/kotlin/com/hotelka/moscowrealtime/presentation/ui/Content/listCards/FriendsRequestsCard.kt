package com.hotelka.moscowrealtime.presentation.ui.Content.listCards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.state.FriendsRequestsUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.NoFriendRequestsForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.FriendRequestItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_friends_requests
import moscowrealtime.composeapp.generated.resources.friends_request
import moscowrealtime.composeapp.generated.resources.loading_friends_requests
import org.jetbrains.compose.resources.stringResource

@Composable
fun FriendsRequestsCard(
    modifier: Modifier,
    friendsRequestsUiState: FriendsRequestsUiState,
    acceptFriendRequest: (FriendRequest) -> Unit,
    declineFriendRequest: (FriendRequest) -> Unit,
    navigateUserProfile: (User) -> Unit,
    onRetryLoadFriendRequests: () -> Unit,
    onDismiss: () -> Unit,
) {
    DismissableCardFrame(
        modifier = modifier,
        onDismissRequest = onDismiss,
        content = {
            DismissableCardHeader(Res.string.friends_request)
            Spacer(Modifier.height(20.dp))
            when (friendsRequestsUiState) {
                is FriendsRequestsUiState.Error -> {
                    ErrorForm(
                        Modifier.fillMaxWidth(), Res.string.error_loading_friends_requests
                    ) { onRetryLoadFriendRequests() }
                }

                is FriendsRequestsUiState.Loading -> {
                    LoadingForm(
                        Modifier.fillMaxWidth(),
                        stringResource(Res.string.loading_friends_requests)
                    )
                }

                is FriendsRequestsUiState.Success -> {
                    val requestsDetailed = friendsRequestsUiState.friendRequests
                    LazyColumn(Modifier.fillMaxSize()) {
                        if (requestsDetailed.isNotEmpty()) {
                            item { Spacer(Modifier.height(10.dp)) }
                            itemsIndexed(requestsDetailed) { index, request ->
                                FriendRequestItem(
                                    modifier = Modifier.padding(5.dp),
                                    user = request.userFrom,
                                    onAccept = { acceptFriendRequest(request.friendRequest) },
                                    onDecline = { declineFriendRequest(request.friendRequest) },
                                    onViewProfile = { navigateUserProfile(request.userFrom) }
                                )
                            }
                            item {
                                Spacer(Modifier.height(120.dp))
                            }
                        } else {
                            item { NoFriendRequestsForm() }
                        }
                    }
                }
            }
        }
    )
}
