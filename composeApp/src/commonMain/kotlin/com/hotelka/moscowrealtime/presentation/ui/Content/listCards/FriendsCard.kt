package com.hotelka.moscowrealtime.presentation.ui.Content.listCards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.NoFriendsForm
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.FriendItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.friends
import moscowrealtime.composeapp.generated.resources.loading_friends
import moscowrealtime.composeapp.generated.resources.loading_friends_error
import org.jetbrains.compose.resources.stringResource

@Composable
fun FriendsCard(
    modifier: Modifier,
    friends: List<User>,
    navigateFindFriends: () -> Unit,
    onViewProfile: (User) -> Unit,
    onDismiss: () -> Unit,
) {
    DismissableCardFrame(
        modifier = modifier,
        onDismissRequest = onDismiss,
        content = {
            DismissableCardHeader(Res.string.friends)
            Spacer(Modifier.height(20.dp))
            if (friends.isEmpty())
                NoFriendsForm(navigateFindFriends)
            else {
                LazyColumn {
                    items(friends) { friend ->
                        FriendItem(
                            modifier = Modifier
                                .padding(5.dp),
                            friend,
                            togetherInQuests = 2,
                            onViewProfile = { onViewProfile(friend) }
                        )
                    }
                    item {
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    )
}