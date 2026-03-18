package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.presentation.events.QuestsPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestsPageUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ShimmeringButton
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.MiniQuestLibrary
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.UserQuestProgressInfo
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.CurrentQuestHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.MapView
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_quest
import moscowrealtime.composeapp.generated.resources.current_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestsPageContent(
    questsPageUiModelState: QuestsPageUiModel,
    onEvent: (QuestsPageEvents) -> Unit
) {
    val animatedMapHeight = animateFloatAsState(
        targetValue = if (questsPageUiModelState.mapIsExpanded) 0.89f else 0.35f,
        animationSpec = tween(300, easing = LinearEasing)
    )
    Box {
        Column(
            modifier = Modifier.fillMaxSize().background(background).padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(Modifier.height(20.dp))
            MapView(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animatedMapHeight.value)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(5.dp)
                    .shadow(3.dp, RoundedCornerShape(10.dp), true, titleTextColor, titleTextColor),
                onMapReady = {
                    onEvent(QuestsPageEvents.OnAttachMap(it) {
                        onEvent(QuestsPageEvents.OnMoveToUserLocation)
                    })
                },
                onRelease = { onEvent(QuestsPageEvents.OnDetachMap) },
                moveToCurrentLocation = { onEvent(QuestsPageEvents.OnMoveToUserLocation) },
                expandActionVisible = true,
                expanded = questsPageUiModelState.mapIsExpanded,
                onExpandMapCalled = { onEvent(QuestsPageEvents.OnMapExpandedChange) },
            )
            LazyColumn(
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item("current_quest") {
                    Text(
                        stringResource(Res.string.current_quest),
                        color = titleTextColor.copy(0.8f),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item("current_quest_title") {
                    CurrentQuestHeader(
                        userCurrentQuestProgressUiState = questsPageUiModelState.questProgressUiState,
                    )
                }
                item("quest_progress_info") {
                    UserQuestProgressInfo(
                        showRange = true,
                        questProgressUiState = questsPageUiModelState.questProgressUiState,
                        onQuestLocationItemClick = { location ->
                            val geoPoint = GeoPoint(location.lat, location.lon, location.label)
                            onEvent(QuestsPageEvents.OnAddPlacemark(geoPoint))
                        }
                    )
                }
                item("quests_library") {
                    MiniQuestLibrary(
                        questsListState = questsPageUiModelState.questsListState,
                        onOpenQuest = { onEvent(QuestsPageEvents.OnNavigateQuestPage(it.id)) },
                        onNavigateLibrary = { onEvent(QuestsPageEvents.OnNavigateQuestsLibrary) },
                        onRetry = { onEvent(QuestsPageEvents.OnRetryLoad) }
                    )
                }
                item("spacer") {
                    Spacer(Modifier.height(200.dp))
                }
            }
        }
        SlideVerticallyCardAnim(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 120.dp)
                .padding(horizontal = 20.dp),
            visible = questsPageUiModelState.proceedButtonVisible,
            content = {
                ShimmeringButton(
                    Res.string.continue_quest,
                    { onEvent(QuestsPageEvents.OnNavigateQuestRoute) }
                )
            }
        )
    }
}
