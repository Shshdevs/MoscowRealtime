package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.usecase.quests.CreateQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchLocationsUseCase
import com.hotelka.moscowrealtime.presentation.controllers.MapManager
import com.hotelka.moscowrealtime.presentation.events.QuestConstructorPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestConstructorPageUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.listState.LocationsListState.Error
import com.hotelka.moscowrealtime.presentation.state.listState.LocationsListState.Success
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.util.toImageBitmap
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestConstructorPageViewModel(
    private val navigator: Navigator,
    private val mapManager: MapManager,
    private val createQuestUseCase: CreateQuestUseCase,
    private val searchLocationsUseCase: SearchLocationsUseCase
) : ViewModel() {

    private val _questConstructorUiModel = MutableStateFlow(QuestConstructorPageUiModel())
    val questConstructorUiModel: StateFlow<QuestConstructorPageUiModel> =
        _questConstructorUiModel.asStateFlow()

    init {
        mapManager.clearMap()
    }
    fun onEvent(event: QuestConstructorPageEvents) {
        fun updateTempQuest(tempQuest: TempQuest) {
            _questConstructorUiModel.update { uiModel ->
                uiModel.copy(tempQuest)
            }
        }
        viewModelScope.launch {
            when (event) {
                is QuestConstructorPageEvents.OnAttachMapView -> {
                    mapManager.attachMap(event.mapView, true, null)
                }

                QuestConstructorPageEvents.OnDetachMapview -> {
                    mapManager.detachMap()
                }

                is QuestConstructorPageEvents.OnAddLocation -> {
                    val newLocations =
                        _questConstructorUiModel.value.tempQuest.locations + event.location
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(locations = newLocations))
                    val geoPoint = event.location.let { GeoPoint(it.lat, it.lon, it.label) }
                    mapManager.addMarker(geoPoint)
                    mapManager.moveTo(geoPoint)
                    mapManager.addRoutePoint(geoPoint).onSuccess { route ->
                        _questConstructorUiModel.update { uiModel -> uiModel.copy(route = route) }
                    }
                }

                is QuestConstructorPageEvents.OnRemoveLocation -> {
                    val newLocations =
                        _questConstructorUiModel.value.tempQuest.locations - event.location
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(locations = newLocations))
                    val geoPoint = event.location.let { GeoPoint(it.lat, it.lon, it.label) }
                    mapManager.removeMarker(geoPoint)
                    mapManager.removeRoutePoint(geoPoint).onSuccess { route ->
                        _questConstructorUiModel.update { uiModel -> uiModel.copy(route = route) }
                    }
                }

                is QuestConstructorPageEvents.OnUpdateQuestLocations -> {
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(locations = event.locations))
                    val geoPoints = event.locations.map { location ->
                        location.let {
                            GeoPoint(
                                it.lat,
                                it.lon,
                                it.label
                            )
                        }
                    }
                    mapManager.updateRoute(geoPoints).onSuccess { route ->
                        _questConstructorUiModel.update { uiModel -> uiModel.copy(route = route) }
                    }
                }

                is QuestConstructorPageEvents.OnSearchLocations -> {
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(searchLocationsQuery = event.query)
                    }
                    val locationsListState = searchLocationsUseCase(event.query).fold(
                        onSuccess = { locations -> Success(locations) },
                        onFailure = { e -> Error(e.message.toString()) }
                    )
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(searchedLocations = locationsListState)
                    }
                }

                QuestConstructorPageEvents.OnSetQuestCover -> {
                    val img = FileKit.openFilePicker(type = FileKitType.Image)
                    img?.let { img ->
                        updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(cover = img.toImageBitmap()))
                    }
                }

                is QuestConstructorPageEvents.OnUpdateQuestDescription -> {
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(description = event.questDescription))
                }

                is QuestConstructorPageEvents.OnUpdateQuestTitle -> {
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(title = event.questTitle))
                }

                QuestConstructorPageEvents.OnSaveQuest -> {
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(isCreatingQuest = true)
                    }
                    createQuestUseCase(_questConstructorUiModel.value.tempQuest).fold(
                        onSuccess = { resultId ->
                            mapManager.clearMap()
                            navigator.navigate(Destination.QuestPage.create(resultId), popUpTo = Destination.QuestConstructorGraph.route, inclusive = true)
                        },
                        onFailure = { e ->
                            _questConstructorUiModel.update { uiModel ->
                                uiModel.copy(errorCreatingQuest = true)
                            }
                        }
                    )
                }

                is QuestConstructorPageEvents.OnAddTag -> {
                    val newTags = _questConstructorUiModel.value.tempQuest.tags + event.tag
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(tags = newTags))
                }

                is QuestConstructorPageEvents.OnRemoveTag -> {
                    val newTags = _questConstructorUiModel.value.tempQuest.tags - event.tag
                    updateTempQuest(_questConstructorUiModel.value.tempQuest.copy(tags = newTags))
                }

                QuestConstructorPageEvents.OnChangeShowEditInfo -> {
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(showEditInfoAlert = !uiModel.showEditInfoAlert)
                    }
                    delay(100)
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(showEditInfoForm = !uiModel.showEditInfoForm)
                    }
                }

                QuestConstructorPageEvents.OnChangeShowExitConstructor -> {
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(exitConstructorAlertVisible = !uiModel.exitConstructorAlertVisible)
                    }
                }

                QuestConstructorPageEvents.OnChangeShowSearchLocations -> {
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(showSearchLocationsAlert = !uiModel.showSearchLocationsAlert)
                    }
                    delay(100)
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(showSearchLocationsForm = !uiModel.showSearchLocationsForm)
                    }
                }

                QuestConstructorPageEvents.OnCloseSavingQuestError -> {
                    _questConstructorUiModel.update { uiModel ->
                        uiModel.copy(errorCreatingQuest = false)
                    }
                }

                QuestConstructorPageEvents.OnGoBack -> {
                    mapManager.clearMap()
                    navigator.back()
                }
            }
        }
    }
}