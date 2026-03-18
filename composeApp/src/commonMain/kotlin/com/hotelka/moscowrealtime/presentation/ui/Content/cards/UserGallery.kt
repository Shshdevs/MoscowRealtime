package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.EmptyUserGallery
import com.hotelka.moscowrealtime.presentation.ui.Content.items.DiscoverItem
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.view_all
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserGallery(
    modifier: Modifier,
    gallery: List<DiscoverDetailed>,
    onOpenGallery: () -> Unit,
    onOpenDiscover: (DiscoverDetailed) -> Unit
) {
    DefaultAppCard(
        modifier.fillMaxWidth().wrapContentHeight(),
        onClick = null,
        content = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                Column {
                    if (gallery.isNotEmpty()) {
                        BoxWithConstraints {
                            val itemWidth = (maxWidth - 5.dp) / 2
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                itemVerticalAlignment = Alignment.CenterVertically,
                                maxItemsInEachRow = 1,
                                maxLines = 2,
                                content = {
                                    gallery.take(4).chunked(2)
                                        .forEachIndexed { index, pair ->
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(5.dp)
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
                        if (gallery.size > 4) {
                            Spacer(Modifier.height(10.dp))
                            Text(
                                stringResource(Res.string.view_all),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = blue,
                                modifier = Modifier.align(Alignment.End)
                                    .padding(end = 10.dp)
                                    .clickable(onClick = onOpenGallery)
                            )
                        }

                    } else {
                        EmptyUserGallery()
                    }

                }
            }
        }
    )
}