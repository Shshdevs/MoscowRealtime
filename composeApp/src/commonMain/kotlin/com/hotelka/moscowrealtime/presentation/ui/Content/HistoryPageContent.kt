package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.events.HistoryPageEvents
import com.hotelka.moscowrealtime.presentation.model.HistoryPageUiModel
import com.hotelka.moscowrealtime.presentation.state.listState.DiscoversListState
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.EmptyHistoryForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.successState.HistoryPageSuccessContent
import com.hotelka.moscowrealtime.presentation.ui.screen.DiscoverPage
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_discovers
import moscowrealtime.composeapp.generated.resources.loading_discovers
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryPageContent(
    historyPageUiModel: HistoryPageUiModel,
    onEvent: (HistoryPageEvents) -> Unit
) {
    Box(Modifier.fillMaxSize().background(background)) {
        when (historyPageUiModel.discoversListState) {
            is DiscoversListState.Error -> {
                ErrorForm(
                    modifier = Modifier.fillMaxSize(),
                    errorDescription = Res.string.error_loading_discovers
                ) { onEvent(HistoryPageEvents.OnRetryLoad) }
            }

            DiscoversListState.Loading -> {
                LoadingForm(Modifier.fillMaxSize(), stringResource(Res.string.loading_discovers))
            }

            is DiscoversListState.Success -> {
                if (historyPageUiModel.discoversListState.discovers.isEmpty()) {
                    EmptyHistoryForm { onEvent(HistoryPageEvents.OnNavigateQuestsPage) }
                } else {
                    HistoryPageSuccessContent(
                        discoversWithDetails = historyPageUiModel.discoversListState.discovers,
                        onOpenDiscover = { onEvent(HistoryPageEvents.OnChangeDiscoverOpened(it)) },
                    )
                }
            }
        }

        SlideVerticallyCardAnim(
            modifier = Modifier.fillMaxSize(),
            visible = historyPageUiModel.discoverOpened != null,
            content = {
                historyPageUiModel.discoverOpened?.let { discover ->
                    DiscoverPage(
                        discoverDetailed = discover,
                        onDismiss = { onEvent(HistoryPageEvents.OnChangeDiscoverOpened(null)) }
                    )
                }
            }
        )
    }
}