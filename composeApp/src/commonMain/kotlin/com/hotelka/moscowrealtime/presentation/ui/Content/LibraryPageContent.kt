package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.LibraryPageEvents
import com.hotelka.moscowrealtime.presentation.model.LibraryPageUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ShimmeringButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.WeeklyTopUsersCard
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SearchQuestsForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.CollapsableHeaderContainer
import com.hotelka.moscowrealtime.presentation.ui.Content.items.QuestItemCard
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.create_quest

@Composable
fun LibraryPageContent(
    libraryPageUiModel: LibraryPageUiModel,
    onEvent: (LibraryPageEvents) -> Unit
) {
    Box(Modifier.fillMaxSize().background(background)) {
        CollapsableHeaderContainer(
            Modifier.fillMaxSize().padding(top = 40.dp),
            maxHeaderHeight = 290.dp,
            headerContent = { headerHeight ->
                WeeklyTopUsersCard(
                    modifier = Modifier.height(headerHeight).padding(10.dp),
                    topWeeklyUsersUiState = libraryPageUiModel.weeklyTopUsers
                )
            },
            content = {
                Box(Modifier.fillMaxSize()) {
                    Column {
                        SearchQuestsForm(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                            finalQuery = libraryPageUiModel.searchQuery,
                            questsListState = libraryPageUiModel.questsListState,
                            questSearchItem = { quest ->
                                QuestItemCard(
                                    quest,
                                    onOpenQuest = {
                                        onEvent(
                                            LibraryPageEvents.OnNavigateQuestPage(
                                                quest.id
                                            )
                                        )
                                    }
                                )
                            },
                            searchQuests = { onEvent(LibraryPageEvents.OnSearchQuests(it)) },
                        )
                    }
                    ShimmeringButton(
                        Res.string.create_quest,
                        onClick = { onEvent(LibraryPageEvents.NavigateQuestConstructor) },
                        modifier = Modifier.align(Alignment.BottomCenter)
                            .fillMaxWidth().padding(horizontal = 10.dp).padding(bottom = 100.dp),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        )
    }
}