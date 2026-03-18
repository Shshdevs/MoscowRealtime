package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetUserDiscoversUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.AcceptFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.DeleteFriendUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.ObserveFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.SendFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.GetOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetUserQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.GetUserScoresUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.ObserveUserUseCase
import com.hotelka.moscowrealtime.presentation.events.UserProfileEvents
import com.hotelka.moscowrealtime.presentation.model.UserProfileUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.FriendRequestState
import com.hotelka.moscowrealtime.presentation.state.UserProfileUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfilePageViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val observeUserUseCase: ObserveUserUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase,
    private val observeFriendRequestUseCase: ObserveFriendRequestUseCase,
    private val getDiscoversDetailsUseCase: GetDiscoversDetailsUseCase,
    private val getUserDiscoversUseCase: GetUserDiscoversUseCase,
    private val getQuestProgressDetailsUseCase: GetQuestProgressDetailsUseCase,
    private val getUserCurrentQuestProgressUseCase: GetQuestProgressUseCase,
    private val getUserScoresUseCase: GetUserScoresUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val getUserQuestsUseCase: GetUserQuestsUseCase,
    private val unFriendUseCase: DeleteFriendUseCase
) : ViewModel() {
    val userId: String get() = savedStateHandle["userId"] ?:""
    private val _userProfileUiState =
        MutableStateFlow<UserProfileUiState>(UserProfileUiState.Loading)
    val userProfileUiState = _userProfileUiState.asStateFlow()
    private val currUserId: String? get() = getCurrentUserIdUseCase()

    init { loadUserProfile() }
    fun onEvent(event: UserProfileEvents) {
        viewModelScope.launch {
            when (event) {
                is UserProfileEvents.OnAcceptFriendRequest -> {
                    acceptFriendRequestUseCase(event.request)
                }

                is UserProfileEvents.OnChangeOpenDiscover -> {
                    if (_userProfileUiState.value is UserProfileUiState.Success) {
                        _userProfileUiState.update { uiState ->
                            (uiState as UserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(openDiscover = event.discover)
                            )
                        }
                    }
                }

                UserProfileEvents.OnChangeOpenGalleryDiscovers -> {
                    if (_userProfileUiState.value is UserProfileUiState.Success) {
                        _userProfileUiState.update { uiState ->
                            (uiState as UserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(isGalleryOpen = !uiState.uiModel.isGalleryOpen)
                            )
                        }
                    }
                }

                UserProfileEvents.CallUnFriendAlert -> {
                    if (_userProfileUiState.value is UserProfileUiState.Success) {
                        _userProfileUiState.update { uiState ->
                            (uiState as UserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(showUnFriendAlert = !uiState.uiModel.showUnFriendAlert)
                            )
                        }
                    }
                }

                is UserProfileEvents.OnUnFriend -> {
                    unFriendUseCase(event.user.id).onSuccess {
                        _userProfileUiState.update { uiState ->
                            (uiState as UserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(showUnFriendAlert = false)
                            )
                        }
                    }
                }

                is UserProfileEvents.OnReload -> {
                    loadUserProfile()
                }

                is UserProfileEvents.OnNavigateQuestPage -> {
                    navigator.navigate(Destination.QuestPage.create(event.questId))
                }

                is UserProfileEvents.OnSendFriendRequest -> {
                    sendFriendRequestUseCase(event.userId)
                }

                UserProfileEvents.OnChangeOpenQuestsInvolved -> {
                    _userProfileUiState.update { uiState ->
                        (uiState as UserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(showQuestsInvolved = !uiState.uiModel.showQuestsInvolved)
                        )
                    }
                }
            }
        }
    }

    fun loadUserProfile() {
        loadUserData(userId)
    }

    private fun loadUserData(userId: String) {
        viewModelScope.launch {
            combine(
                observeUserUseCase(userId),
                observeFriendRequestUseCase(userId)
            ) { userResult, friendRequestResult ->
                val user = userResult.getOrNull()
                val friendRequest = friendRequestResult.getOrNull()
                if (user != null) {
                    val friendRequestState =
                        if (friendRequest == null) FriendRequestState.None else FriendRequestState.Success(
                            friendRequest
                        )
                    if (_userProfileUiState.value !is UserProfileUiState.Success) {
                        val uiModel =
                            loadUserProfileUiState(user).copy(
                                friendRequestState = friendRequestState,
                                showProfile = user.friends.contains(currUserId) || !user.profilePrivate,
                                showPhotos = user.friends.contains(currUserId) || !user.photosPrivate
                            )
                        UserProfileUiState.Success(uiModel)
                    } else {
                        (_userProfileUiState.value as UserProfileUiState.Success).copy(
                            uiModel = (_userProfileUiState.value as UserProfileUiState.Success).uiModel.copy(
                                friendRequestState = friendRequestState
                            )
                        )
                    }
                } else {
                    UserProfileUiState.Error(
                        userResult.exceptionOrNull()?.message ?: "Unknown error"
                    )
                }
            }.collect { uiModel ->
                _userProfileUiState.value = uiModel
            }
        }
    }

    private suspend fun loadUserProfileUiState(user: User): UserProfileUiModel {
        return coroutineScope {
            val scoresDeferred = async { getUserScoresUseCase(user.id).getOrDefault(emptyList()) }
            val organizationDeferred =
                async { user.organizationId?.let { getOrganizationUseCase(it).getOrNull() } }
            val questProgressDeferred =
                async { user.currentQuestProgress?.let { getUserCurrentQuestProgressUseCase(it).getOrNull() } }
            val discoversDeferred =
                async { getUserDiscoversUseCase(user.id).getOrDefault(emptyList()) }
            val questsDeferred = async { getUserQuestsUseCase(user.id).getOrDefault(emptyList()) }

            val quests = questsDeferred.await()
            val scores = scoresDeferred.await()
            val organization = organizationDeferred.await()
            val questProgress =
                questProgressDeferred.await()?.let { getQuestProgressDetailsUseCase(it) }
            val discovers = getDiscoversDetailsUseCase(discoversDeferred.await())

            UserProfileUiModel(
                user = user,
                scores = scores,
                discovers = discovers,
                questsInvolved = quests,
                organization = organization,
                questProgress = questProgress,
            )
        }
    }
}