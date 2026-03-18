package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.descriptions.DiscoverDescription
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DiscoverFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DiscoverHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.DiscoverHeaderInfo
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun DiscoverContent(
    discoverDetailed: DiscoverDetailed,
    enableShowWhatAround: Boolean,
    onShowAround: (Location) -> Unit,
    onDismiss: () -> Unit
) {
    val hazeState = rememberHazeState()

    DiscoverFrame(
        onDismiss = onDismiss,
        headerContent = { appBarHeight ->
            DiscoverHeader(
                modifier = Modifier,
                hazeState = hazeState,
                height = appBarHeight,
                image = discoverDetailed.discover.imageSavedUrl
            )
        },
        headerInfo = { titleHeight ->
            DiscoverHeaderInfo(
                enableShowWhatAround = enableShowWhatAround,
                modifier = Modifier,
                location = discoverDetailed.location,
                height = titleHeight,
                hazeState = hazeState,
                onShowAround = onShowAround
            )
        },
        mainContent = { listState ->
            DiscoverDescription(
                lazyListState = listState,
                discoverDetailed = discoverDetailed,
            )
        }
    )
}