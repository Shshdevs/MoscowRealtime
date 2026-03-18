package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SearchQuestsForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.items.PinQuestItemCard

@Composable
fun PinQuestCard(
    visible: Boolean,
    finalQuery: String,
    onSearchQuests: (String) -> Unit,
    questsSearchStateList: QuestsListState,
    pinQuest: (Quest) -> Unit,
    navigateQuestPage: (Quest) -> Unit,
    onDismiss: () -> Unit
) {
    SlideVerticallyCardAnim(
        modifier = Modifier.padding(top = 60.dp).fillMaxSize(),
        visible = visible,
        content = {
            DismissableCardFrame(
                onDismissRequest = onDismiss,
                modifier = Modifier.fillMaxSize(),
            ) {
                Column {
                    Spacer(Modifier.height(10.dp))
                    SearchQuestsForm(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                        finalQuery = finalQuery,
                        questsListState = questsSearchStateList,
                        questSearchItem = { quest ->
                            PinQuestItemCard(
                                quest = quest,
                                pinQuest = pinQuest,
                                navigateQuestPage = navigateQuestPage
                            )
                        },
                        searchQuests = onSearchQuests,
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    )
}