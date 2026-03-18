package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.QuestConstructorPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestConstructorPageUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.EditQuestInfoAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ErrorSavingQuestAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ExitQuestConstructorAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.LoadingAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.SearchLocationsAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.QuestConstructorRouteManager
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.QuestConstructorRouteMapPreview
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.QuestPreviewInfo
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import moscowrealtime.composeapp.generated.resources.creating_quest_load
import moscowrealtime.composeapp.generated.resources.quest_constructor
import moscowrealtime.composeapp.generated.resources.save
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestConstructorContent(
    questConstructorPageUiModel: QuestConstructorPageUiModel,
    onEvent: (QuestConstructorPageEvents) -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    var mapIsInteracted by remember { mutableStateOf(false) }
    var locationsDragInProgress by remember { mutableStateOf(false) }

    Box {
        Column(
            Modifier
                .fillMaxSize()
                .background(background)
        ) {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(5.dp),
                title = {
                    Text(
                        stringResource(Res.string.quest_constructor),
                        color = titleTextColor.copy(0.8f),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    TextButton(onClick = {
                        if (questConstructorPageUiModel.saveQuestEnabled()) {
                            onEvent(QuestConstructorPageEvents.OnSaveQuest)
                        }
                    }
                    ) {
                        Text(
                            stringResource(Res.string.save),
                            color = blue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(QuestConstructorPageEvents.OnChangeShowExitConstructor) },
                    ) {
                        Icon(
                            painterResource(Res.drawable.arrow_previous),
                            "Go back",
                            tint = blue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(background)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                userScrollEnabled = !mapIsInteracted && !locationsDragInProgress,
                state = lazyColumnState,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item("quest_info_preview") {
                    QuestPreviewInfo(
                        tempQuest = questConstructorPageUiModel.tempQuest,
                        onShowEditInfoAlert = { onEvent(QuestConstructorPageEvents.OnChangeShowEditInfo) }
                    )
                }
                item("quest_route_manage") {
                    QuestConstructorRouteManager(
                        tempQuest = questConstructorPageUiModel.tempQuest,
                        onShowSearchLocations = { onEvent(QuestConstructorPageEvents.OnChangeShowSearchLocations) },
                        onUpdateLocations = { locations ->
                            onEvent(
                                QuestConstructorPageEvents.OnUpdateQuestLocations(
                                    locations
                                )
                            )
                        },
                        onRemoveLocation = { onEvent(QuestConstructorPageEvents.OnRemoveLocation(it)) },
                        onDragStateChanged = { locationsDragInProgress = it }
                    )
                }
                item("map_preview") {
                    QuestConstructorRouteMapPreview(
                        locationsSize = questConstructorPageUiModel.tempQuest.locations.size,
                        route = questConstructorPageUiModel.route,
                        onMapReady = { onEvent(QuestConstructorPageEvents.OnAttachMapView(it)) },
                        onRelease = { onEvent(QuestConstructorPageEvents.OnDetachMapview) },
                        onMapTouchStateChanged = { mapIsInteracted = it }
                    )
                }
            }
            if (questConstructorPageUiModel.showSearchLocationsAlert) {
                SearchLocationsAlert(
                    contentVisible = questConstructorPageUiModel.showSearchLocationsForm,
                    tempQuest = questConstructorPageUiModel.tempQuest,
                    searchedLocations = questConstructorPageUiModel.searchedLocations,
                    finalQuery = questConstructorPageUiModel.searchLocationsQuery,
                    onAddLocation = { onEvent(QuestConstructorPageEvents.OnAddLocation(it)) },
                    onSearchLocations = { onEvent(QuestConstructorPageEvents.OnSearchLocations(it)) },
                    onDismiss = { onEvent(QuestConstructorPageEvents.OnChangeShowSearchLocations) }
                )
            }
            if (questConstructorPageUiModel.showEditInfoAlert) {
                EditQuestInfoAlert(
                    contentVisible = questConstructorPageUiModel.showEditInfoForm,
                    tempQuest = questConstructorPageUiModel.tempQuest,
                    onEvent = onEvent,
                )
            }
            if (questConstructorPageUiModel.exitConstructorAlertVisible) {
                ExitQuestConstructorAlert(
                    onSubmit = { onEvent(QuestConstructorPageEvents.OnGoBack) },
                    onDismiss = { onEvent(QuestConstructorPageEvents.OnChangeShowExitConstructor) }
                )
            }
            if (questConstructorPageUiModel.errorCreatingQuest) {
                ErrorSavingQuestAlert { onEvent(QuestConstructorPageEvents.OnCloseSavingQuestError) }
            }
        }
        if (questConstructorPageUiModel.isCreatingQuest) LoadingAlert(stringResource(Res.string.creating_quest_load))
    }
}