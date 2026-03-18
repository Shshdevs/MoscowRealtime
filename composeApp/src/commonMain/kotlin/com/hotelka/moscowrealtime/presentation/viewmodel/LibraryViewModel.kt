package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.model.TopWeeklyUser
import com.hotelka.moscowrealtime.domain.model.WeeklyScore
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.GetWeeklyTopUsersUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.presentation.events.LibraryPageEvents
import com.hotelka.moscowrealtime.presentation.model.LibraryPageUiModel
import com.hotelka.moscowrealtime.presentation.model.TopWeeklyUsersUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.TopWeeklyUsersUiState
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val navigator: Navigator,
    private val searchQuestsUseCase: SearchQuestsUseCase,
    private val getQuestsUseCase: GetQuestsUseCase,
    private val getWeeklyTopUsersUseCase: GetWeeklyTopUsersUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
) : ViewModel() {

    private val _libraryPageUiModel = MutableStateFlow(LibraryPageUiModel())
    val libraryPageUiModel: StateFlow<LibraryPageUiModel> = _libraryPageUiModel.asStateFlow()

    fun onEvent(event: LibraryPageEvents) {
        when (event) {
            LibraryPageEvents.OnReload -> {
                searchQuests(_libraryPageUiModel.value.searchQuery)
                getWeeklyTopUserScores()
            }
            LibraryPageEvents.NavigateQuestConstructor -> {
                navigator.navigate(Destination.QuestConstructorGraph.route)
            }
            is LibraryPageEvents.OnNavigateQuestPage -> {
                navigator.navigate(Destination.QuestPage.create(event.questId))
            }

            is LibraryPageEvents.OnSearchQuests -> {
                if (_libraryPageUiModel.value.searchQuery != event.query) {
                    _libraryPageUiModel.update { uiModel ->
                        uiModel.copy(searchQuery = event.query)
                    }
                    searchQuests(event.query)
                }
            }


        }
    }

    init {
        searchQuests("")
        getWeeklyTopUserScores()
    }

    private fun searchQuests(query: String) {
        viewModelScope.launch {
            _libraryPageUiModel.update { uiModel ->
                uiModel.copy(questsListState = QuestsListState.Loading)
            }

            val questsState = if (query.isEmpty()) {
                getQuestsUseCase().fold(
                    onSuccess = { quests ->
                        QuestsListState.Success(quests)
                    },
                    onFailure = { e ->
                        QuestsListState.Error(e.message.toString())
                    }
                )
            } else {
                searchQuestsUseCase(query).fold(
                    onSuccess = { quests ->
                        QuestsListState.Success(quests)
                    },
                    onFailure = { e ->
                        QuestsListState.Error(e.message.toString())
                    }
                )
            }

            _libraryPageUiModel.update { uiModel ->
                uiModel.copy(questsListState = questsState)
            }
        }
    }

    private fun getWeeklyTopUserScores() {
        viewModelScope.launch {
            getWeeklyTopUsersUseCase().collect { result ->
                val weeklyTopUsers = result.fold(
                    onSuccess = { scores ->
                        val weeklyTop = getScoresDetails(scores)
                        TopWeeklyUsersUiState.Success(weeklyTop)
                    },
                    onFailure = { e ->
                        TopWeeklyUsersUiState.Error(e.message.toString())
                    }
                )
                _libraryPageUiModel.update { uiModel ->
                    uiModel.copy(weeklyTopUsers = weeklyTopUsers)
                }
            }

        }
    }

    private suspend fun getScoresDetails(scores: List<WeeklyScore>): TopWeeklyUsersUiModel {
        val usersIds = scores.map { it.userId }.distinct()
        return coroutineScope {
            val usersDeferred = async {
                val users = getUsersByIdsUseCase(usersIds).getOrNull()
                users?.associateBy { it.id } ?: emptyMap()
            }

            val users = usersDeferred.await()

            val topWeeklyUsers = scores.mapNotNull { score ->
                users[score.userId]?.let { user ->
                    TopWeeklyUser(
                        user = user,
                        score = score
                    )
                }
            }

            TopWeeklyUsersUiModel(topWeeklyUsers)
        }
    }

}