package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.DeleteQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.StartQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchFriendsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.ContinueQuestUseCase
import com.hotelka.moscowrealtime.presentation.events.QuestPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestPageUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestPageViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getQuestDetailsUseCase: GetQuestDetailsUseCase,
    private val startQuestUseCase: StartQuestUseCase,
    private val continueQuestUseCase: ContinueQuestUseCase,
    private val searchFriendsUseCase: SearchFriendsUseCase,
    private val deleteQuestUseCase: DeleteQuestUseCase
) : ViewModel() {
    val questId: String = savedStateHandle["questId"] ?: ""

    private val currUserId: String? get() = getCurrentUserIdUseCase()
    private val _questUiModelState = MutableStateFlow(QuestPageUiModel())
    val questUiModelState: StateFlow<QuestPageUiModel> = _questUiModelState.asStateFlow()

    init {
        loadQuestPage(questId)
    }

    fun onEvent(event: QuestPageEvents) {
        when (event) {
            QuestPageEvents.OnNavigateBack -> {
                navigator.back()
            }

            QuestPageEvents.OnReload -> {
                loadQuestPage(_questUiModelState.value.questId)
            }

            is QuestPageEvents.OnContinueQuest -> {
                viewModelScope.launch {
                    continueQuestUseCase(event.questProgressId).onSuccess {
                        navigator.navigate(
                            Destination.CurrQuestProgressPage.route
                        )
                    }
                }
            }

            is QuestPageEvents.OnStartQuest -> {
                viewModelScope.launch {
                    startQuestUseCase(
                        _questUiModelState.value.questId,
                        currUserId!!,
                        event.usersInvited
                    ).onSuccess {
                        navigator.navigate(
                            Destination.CurrQuestProgressPage.route
                        )
                    }
                }
            }

            QuestPageEvents.OnChangeShowStartQuestCard -> {
                _questUiModelState.update { uiModel ->
                    uiModel.copy(showFriendsCard = !uiModel.showFriendsCard)
                }
            }

            is QuestPageEvents.OnSearchFriends -> {
                searchFriends(event.query)
            }

            is QuestPageEvents.OnOpenAuthorProfile -> {
                if (event.userId != currUserId){
                    navigator.navigate(Destination.UserProfilePage.create(event.userId))
                }
            }

            QuestPageEvents.OnChangeShowDeleteQuestAlert -> {
                _questUiModelState.update { uiModel ->
                    uiModel.copy(showDeleteQuestAlert = !uiModel.showDeleteQuestAlert)
                }
            }

            QuestPageEvents.OnDeleteQuest -> {
                viewModelScope.launch {
                    deleteQuestUseCase(questUiModelState.value.questId).onSuccess {
                        navigator.back()
                    }
                }
            }
        }
    }

    fun loadQuestPage(questId: String) {
        _questUiModelState.update { uiModel ->
            uiModel.copy(questId = questId)
        }
        viewModelScope.launch {
            var userIsQuestAuthor = false
            var showAuthorPhoto = false
            val questState = try {
                val questPage = getQuestDetailsUseCase(questId)
                if (questPage != null) {
                    userIsQuestAuthor = (questPage.userAuthor?.id ?: "null") == currUserId
                    showAuthorPhoto =
                        userIsQuestAuthor || questPage.userAuthor?.friends?.contains(currUserId) == true
                                || questPage.userAuthor?.profilePrivate == false
                    QuestUiModelState.Success(questDetailed = questPage)
                } else {
                    QuestUiModelState.Error(UserNotAuthenticatedException().message.toString())
                }
            } catch (e: Exception) {
                QuestUiModelState.Error(e.message.toString())
            }
            _questUiModelState.update { uiModel ->
                uiModel.copy(
                    questUiModelState = questState,
                    showAuthorPhoto = showAuthorPhoto,
                    currUserIsAuthor = userIsQuestAuthor
                )
            }
        }
    }

    fun searchFriends(query: String) {
        val currUserId = currUserId ?: return
        viewModelScope.launch {
            _questUiModelState.update { uiModel ->
                uiModel.copy(searchedFriends = UsersListState.Loading)
            }
            val searchResult = searchFriendsUseCase(currUserId, query).fold(
                onSuccess = { users -> UsersListState.Success(users) },
                onFailure = { e -> UsersListState.Error(e.message.toString()) }
            )
            _questUiModelState.update { uiModel ->
                uiModel.copy(searchedFriends = searchResult)
            }
        }
    }
}