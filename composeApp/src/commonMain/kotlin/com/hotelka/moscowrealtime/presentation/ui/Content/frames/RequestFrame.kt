package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.RequestState
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.RunningRequestUnrecognizedCard
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import kotlinx.coroutines.delay
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.almost_sure
import moscowrealtime.composeapp.generated.resources.analyzing
import moscowrealtime.composeapp.generated.resources.classifying
import moscowrealtime.composeapp.generated.resources.request_error
import org.jetbrains.compose.resources.stringResource

@Composable
fun RequestFrame(
    modifier: Modifier,
    requestState: RequestState,
    onRecognized: @Composable ColumnScope.(DiscoverDetailed) -> Unit,
    onDismissRequest: () -> Unit,
    onContinue: () -> Unit = onDismissRequest,
) {
    SlideVerticallyCardAnim(
        visible = requestState !is RequestState.None,
        content = {
            DismissableCardFrame(
                modifier = modifier,
                dismissAvailable = requestState !is RequestState.Running,
                onDismissRequest = onDismissRequest,
                content = {
                    Column(verticalArrangement = Arrangement.Center) {
                        when (requestState) {
                            is RequestState.Error -> {
                                ErrorForm(
                                    Modifier.fillMaxSize(),
                                    errorDescription = Res.string.request_error,
                                    onRetry = onDismissRequest
                                )
                            }

                            RequestState.None -> {}
                            is RequestState.Recognized -> {
                                onRecognized(requestState.discover)
                            }

                            RequestState.Running -> {
                                val resources = listOf(
                                    Res.string.analyzing,
                                    Res.string.classifying,
                                    Res.string.almost_sure
                                )
                                var i by remember { mutableIntStateOf(0) }
                                LaunchedEffect(i) {
                                    delay(5000)
                                    if (i < 2) i++ else i == 0
                                }
                                LoadingForm(
                                    Modifier.fillMaxSize().background(background),
                                    stringResource(resources[i])
                                )
                            }

                            RequestState.Unrecognized -> {
                                RunningRequestUnrecognizedCard(
                                    modifier = Modifier.fillMaxSize(),
                                    onContinueQuest = onContinue
                                )
                            }
                        }
                    }
                }
            )
        }
    )
}