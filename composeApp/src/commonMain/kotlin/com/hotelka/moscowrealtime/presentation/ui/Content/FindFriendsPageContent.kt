package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.FindFriendsPageEvents
import com.hotelka.moscowrealtime.presentation.model.FindFriendsPageUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SearchUsersForm
import com.hotelka.moscowrealtime.presentation.ui.Content.items.UserItem

@Composable
fun FindFriendsPageContent(
    findFriendsPageUiModel: FindFriendsPageUiModel,
    onEvent: (FindFriendsPageEvents) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(),) {
        SearchUsersForm(
            modifier = Modifier.fillMaxSize().padding(top = 80.dp, bottom = 100.dp),
            finalQuery = findFriendsPageUiModel.searchFinalQuery,
            usersListState = findFriendsPageUiModel.searchedUsers,
            searchUsers = { onEvent(FindFriendsPageEvents.Search(it)) },
            userSearchItem = { user ->
                UserItem(
                    modifier = Modifier.fillMaxWidth(),
                    user
                ) { onEvent(FindFriendsPageEvents.NavigateUserProfile(user.id)) }
            },
        )
    }
}