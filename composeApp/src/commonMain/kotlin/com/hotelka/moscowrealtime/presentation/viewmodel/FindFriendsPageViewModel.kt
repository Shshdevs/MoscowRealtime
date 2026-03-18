package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.usecase.search.SearchUsersUseCase
import com.hotelka.moscowrealtime.presentation.events.FindFriendsPageEvents
import com.hotelka.moscowrealtime.presentation.model.FindFriendsPageUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FindFriendsPageViewModel(
    private val navigator: Navigator,
    private val searchUsersUseCase: SearchUsersUseCase,
) : ViewModel() {
    private val _findFriendsPageUiModel = MutableStateFlow(FindFriendsPageUiModel())
    val findFriendsPageUiModel: StateFlow<FindFriendsPageUiModel> =
        _findFriendsPageUiModel.asStateFlow()

    fun onEvent(event: FindFriendsPageEvents){
        when(event){
            is FindFriendsPageEvents.NavigateUserProfile -> {navigator.navigate(Destination.UserProfilePage.create(event.userId))}
            FindFriendsPageEvents.RetryLoadUsers -> { searchUsers() }
            is FindFriendsPageEvents.Search -> {
                _findFriendsPageUiModel.update { uiModel -> uiModel.copy(searchFinalQuery = event.query) }
                searchUsers()
            }
        }
    }

    private fun searchUsers() {
        viewModelScope.launch {
            val users = searchUsersUseCase(_findFriendsPageUiModel.value.searchFinalQuery).fold(
                onSuccess = { users -> UsersListState.Success(users) },
                onFailure = { e -> UsersListState.Error(e.message.toString()) }
            )
            _findFriendsPageUiModel.update { uiModel -> uiModel.copy(searchedUsers = users) }
        }
    }
}