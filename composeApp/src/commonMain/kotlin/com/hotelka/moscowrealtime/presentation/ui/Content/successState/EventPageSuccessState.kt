package com.hotelka.moscowrealtime.presentation.ui.Content.successState

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.ViewSiteButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.PlaceInfoCard
import com.hotelka.moscowrealtime.presentation.ui.Content.descriptions.EventDescription
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.CollapsableHeaderContainer
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.PageHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.EventDateInfo
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.EventPlaceInfo
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.PriceInfo
import com.hotelka.moscowrealtime.presentation.ui.Custom.PageTitle
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventPageSuccessState(
    event: DetailedEvent,
    onNavigateBack: () -> Unit,
    onOpenLink: (String) -> Unit,
    navigateEventMapRoute: () -> Unit,
) {
    CollapsableHeaderContainer(
        headerContent = { headerHeight ->
            val showTags = headerHeight.value.roundToInt() > 165
            PageHeader(
                modifier = Modifier,
                cover = event.images.getOrNull(0)?.image ?: "",
                tags = event.tags.sortedBy { it.length },
                showTags = showTags,
                onNavigateBack = onNavigateBack
            )
        },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(20.dp),
            ) {
                item("title") {
                    PageTitle(title = event.title)
                }

                item("description") {
                    EventDescription(description = event.description)
                }

                item("view_site_button") {
                    ViewSiteButton(
                        onClick = { onOpenLink(event.siteUrl) }
                    )
                }

                item("date") {
                    EventDateInfo(dates = event.dates)
                }

                item("price") {
                    PriceInfo(price = event.price)
                }

                item("place") {
                    if (event.detailedPlace != null) {
                        PlaceInfoCard(
                            modifier = Modifier.fillMaxWidth(),
                            place = event.detailedPlace,
                            onClick = {
                                if (event.detailedPlace.coords?.longitude != null && event.detailedPlace.coords.latitude != null) {
                                    navigateEventMapRoute()
                                }
                            }
                        )
                    } else {
                        EventPlaceInfo(event.detailedPlace)
                    }
                }

                item("spacer") {
                    Spacer(Modifier.height(200.dp))
                }
            }
        }
    )


}