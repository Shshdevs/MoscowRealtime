package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.QuestPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestPageUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.QuestPageMainContent
import com.hotelka.moscowrealtime.presentation.ui.Custom.PageTitle
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_quest
import moscowrealtime.composeapp.generated.resources.loading_quest
import moscowrealtime.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestPageInfoContent(
    questPageUiModel: QuestPageUiModel,
    onEvent: (QuestPageEvents) -> Unit

) {

    LazyColumn(
        modifier = Modifier.background(background).padding(PaddingValues(top = 10.dp)),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item("header") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                when (questPageUiModel.questUiModelState) {
                    is QuestUiModelState.Error -> {
                        PageTitle(title = stringResource(Res.string.error_loading_quest))
                    }

                    QuestUiModelState.Loading -> {
                        PageTitle(title = stringResource(Res.string.loading_quest))
                    }

                    is QuestUiModelState.Success -> {
                        PageTitle(title = questPageUiModel.questUiModelState.questDetailed.quest.title)
                    }
                }
                if (questPageUiModel.currUserIsAuthor) {
                    Spacer(Modifier.width(10.dp).weight(1f))
                    IconButton(
                        onClick = { onEvent(QuestPageEvents.OnChangeShowDeleteQuestAlert) },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = background,
                            containerColor = red
                        ),
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape
                    ) {
                        Icon(painterResource(Res.drawable.trash), "Delete Quest")
                    }
                }
            }
        }
        item("content") {
            QuestPageMainContent(
                questPageUiModel = questPageUiModel,
                onEvent
            )
        }

        item("spacer") {
            Spacer(Modifier.height(200.dp))
        }
    }
}