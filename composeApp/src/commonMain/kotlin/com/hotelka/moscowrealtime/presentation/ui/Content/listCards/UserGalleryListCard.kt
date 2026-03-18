package com.hotelka.moscowrealtime.presentation.ui.Content.listCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.DiscoverItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.user_gallery

@Composable
fun UserGalleryListCard(
    modifier: Modifier,
    gallery: List<DiscoverDetailed>,
    onOpenDiscover: (DiscoverDetailed) -> Unit,
    onDismiss: () -> Unit
) {
    DismissableCardFrame(
        modifier,
        onDismissRequest = onDismiss,
        content = {
            DismissableCardHeader(Res.string.user_gallery)
            LazyColumn {
                item {
                    BoxWithConstraints {
                        val itemWidth = (maxWidth - 5.dp) / 2
                        FlowRow(
                            modifier = Modifier.padding(10.dp).fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            itemVerticalAlignment = Alignment.CenterVertically,
                            content = {
                                gallery.chunked(2)
                                    .forEachIndexed { index, pair ->
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(
                                                5.dp
                                            )
                                        ) {
                                            DiscoverItem(
                                                modifier = Modifier.height(145.dp)
                                                    .weight(1f)
                                                    .width(if (pair.size == 1) itemWidth * 2 else itemWidth),
                                                discover = pair[0].discover,
                                                viewHistory = { onOpenDiscover(pair[0]) },
                                                location = pair[0].location,
                                            )
                                            if (pair.size > 1) {
                                                DiscoverItem(
                                                    modifier = Modifier.height(145.dp)
                                                        .weight(1f)
                                                        .width(itemWidth),
                                                    discover = pair[1].discover,
                                                    viewHistory = { onOpenDiscover(pair[1]) },
                                                    location = pair[1].location,
                                                )
                                            }
                                        }
                                    }

                            }
                        )
                    }

                }
                item("spacer") {
                    Spacer(Modifier.height(120.dp))
                }
            }
        }
    )
}