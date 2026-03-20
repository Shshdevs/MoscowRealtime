package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.presentation.events.QuestProgressGraphEvents
import com.hotelka.moscowrealtime.presentation.model.QuestProgressGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.InteractiveMapCameraMenu
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.ScaleAnimContainer
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.QuitMapsButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.QuestMapCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.RunningRequestSuccessCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.RunningRequestWrongCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.ThePointIsReachedByUserCard
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.CameraFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.MapView
import com.hotelka.moscowrealtime.presentation.ui.screen.DiscoverPage
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.back_to_quests
import moscowrealtime.composeapp.generated.resources.verifying
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestMapContent(
    questProgressGraphUiModel: QuestProgressGraphUiModel,
    onEvent: (QuestProgressGraphEvents) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        MapView(
            modifier = Modifier.fillMaxSize(),
            onMapReady = { mapView ->
                onEvent(QuestProgressGraphEvents.OnAttachMap(mapView) {
                    val location = questProgressGraphUiModel.awaitedLocation ?: return@OnAttachMap
                    val locationGeoPoint = GeoPoint(location.lat, location.lon, location.label)
                    onEvent(
                        QuestProgressGraphEvents.OnSetPlacemark(locationGeoPoint)
                    )
                })
            },
            onRelease = { onEvent(QuestProgressGraphEvents.OnDetachMap) },
            showMyLocationButton = false
        )

        if (!questProgressGraphUiModel.cameraModeOn) {
            QuitMapsButton(
                Modifier.align(Alignment.TopStart),
                Res.string.back_to_quests
            ) { onEvent(QuestProgressGraphEvents.OnNavigateBack) }
        }
        ScaleAnimContainer(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(10.dp)
                .padding(bottom = 20.dp),
            visible = !questProgressGraphUiModel.cameraModeOn,
            content = {
                QuestMapCard(
                    questProgressUiState = questProgressGraphUiModel.questProgressUiState,
                    progress = questProgressGraphUiModel.questProgressValue,
                    routeInfo = questProgressGraphUiModel.routeInfo,
                    onOpenCamera = { onEvent(QuestProgressGraphEvents.OnChangeCameraMode) },
                    onMoveToUserLocation = { onEvent(QuestProgressGraphEvents.OnMoveToUserLocation) }
                )
            }
        )

        if (questProgressGraphUiModel.cameraModeOn) {
            CameraFrame(
                onRecognized = { discover, resetRequestState ->
                    var discover by remember{mutableStateOf(discover)}
                    val quest =
                        (questProgressGraphUiModel.questProgressUiState as QuestProgressUiState.Success).questProgressDetailed.quest
                    LaunchedEffect(discover.discover.id){
                        onEvent(
                            QuestProgressGraphEvents.OnVerifyResult(
                                discover = discover.discover,
                                questId = quest.id,
                                score = if (discover.location?.id == quest.locations.last()) 15 else 5
                            )
                        )
                    }
                    if (questProgressGraphUiModel.verificationQuestResult != null) {
                        RunningRequestSuccessCard(
                            modifier = Modifier.fillMaxSize(),
                            visible = questProgressGraphUiModel.verificationQuestResult.success,
                            location = discover.location!!,
                            onShowContinueToResult = {
                                onEvent(QuestProgressGraphEvents.OnShowDiscover(discover))
                            },
                            onContinueQuest = {
                                onEvent(QuestProgressGraphEvents.OnResetVerificationResult)
                                resetRequestState()
                                onEvent(QuestProgressGraphEvents.OnNavigateBack)
                            }
                        )
                        RunningRequestWrongCard(
                            modifier = Modifier.fillMaxSize(),
                            visible = !questProgressGraphUiModel.verificationQuestResult.success,
                            location = discover.location!!,
                            onShowContinueToResult = {
                                onEvent(QuestProgressGraphEvents.OnShowDiscover(discover))
                            },
                            onContinueQuest = {
                                onEvent(QuestProgressGraphEvents.OnResetVerificationResult)
                                resetRequestState()
                            }
                        )
                    } else {
                        LoadingForm(
                            Modifier.fillMaxSize(), stringResource(Res.string.verifying)
                        )
                    }
                },
                menuContent = { pickFromGallery, takePic ->
                    ScaleAnimContainer(
                        modifier = Modifier.align(Alignment.BottomCenter)
                            .padding(bottom = 80.dp),
                        visible = questProgressGraphUiModel.cameraModeOn,
                        content = {
                            InteractiveMapCameraMenu(
                                Modifier.align(Alignment.BottomCenter),
                                onPickGallery = pickFromGallery,
                                onTakePic = takePic,
                                onShowMap = { onEvent(QuestProgressGraphEvents.OnChangeCameraMode) }
                            )
                        }
                    )
                },
                navigateBack = { onEvent(QuestProgressGraphEvents.OnNavigateBack) },
            )
        }

        SlideVerticallyCardAnim(
            visible = questProgressGraphUiModel.pointIsReachedByUser != null,
            content = {
                questProgressGraphUiModel.pointIsReachedByUser?.let {
                    ThePointIsReachedByUserCard(
                        user = it,
                        location = questProgressGraphUiModel.awaitedLocation!!,
                        onContinueQuest = { onEvent(QuestProgressGraphEvents.OnNavigateBack) }
                    )
                }
            }
        )
        SlideVerticallyCardAnim(
            visible = questProgressGraphUiModel.showDiscover != null,
            content = {
                questProgressGraphUiModel.showDiscover?.let {
                    DiscoverPage(
                        enableShowWhatAround = false,
                        discoverDetailed = it,
                        onDismiss = { onEvent(QuestProgressGraphEvents.OnShowDiscover(null)) }
                    )
                }
            }
        )
    }
}