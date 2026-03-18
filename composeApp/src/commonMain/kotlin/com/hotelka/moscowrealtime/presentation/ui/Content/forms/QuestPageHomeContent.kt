package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.model.QuestPageUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ShimmeringButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.QuestCreatedByCard
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_quest
import moscowrealtime.composeapp.generated.resources.error_loading_quest
import moscowrealtime.composeapp.generated.resources.loading_quest
import moscowrealtime.composeapp.generated.resources.start_continue_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestPageHomeContent(
    questPageUiModel: QuestPageUiModel,
    onOpenAuthorProfile: (User) -> Unit,
    onRetry: () -> Unit,
    onStartQuest: () -> Unit,
    onContinueQuest: () -> Unit,
) {
    Column {
        when (questPageUiModel.questUiModelState) {
            is QuestUiModelState.Error -> {
                ErrorForm(
                    Modifier.fillMaxWidth(),
                    errorDescription = Res.string.error_loading_quest,
                    onRetry = onRetry
                )
            }

            QuestUiModelState.Loading -> {
                LoadingForm(
                    modifier = Modifier.fillMaxWidth(),
                    stringResource(Res.string.loading_quest)
                )
            }

            is QuestUiModelState.Success -> {
                Text(
                    questPageUiModel.questUiModelState.questDetailed.quest.description,
                    style = TextStyle.Default.copy(
                        color = secondaryTextColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    )
                )
                Spacer(Modifier.height(10.dp))

                if (questPageUiModel.questUiModelState.questDetailed.questProgress != null) {
                    ShimmeringButton(
                        Res.string.continue_quest,
                        onContinueQuest,
                        shape = RoundedCornerShape(10.dp)
                    )
                } else {
                    ShimmeringButton(
                        Res.string.start_continue_quest,
                        onStartQuest,
                        shape = RoundedCornerShape(10.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))

                questPageUiModel.questUiModelState.questDetailed.userAuthor?.let { author ->
                    QuestCreatedByCard(
                        user = author,
                        showAuthorPhotos = questPageUiModel.showAuthorPhoto
                    ) { onOpenAuthorProfile(author) }
                }
            }
        }
    }
}