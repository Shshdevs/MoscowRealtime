package com.hotelka.moscowrealtime.presentation.ui.Content.successState

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.items.DiscoverItem
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.your_story
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryPageSuccessContent(
    discoversWithDetails: List<DiscoverDetailed>,
    onOpenDiscover: (DiscoverDetailed) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
        item {
            Spacer(Modifier.Companion.height(40.dp))
        }
        item("title") {
            Text(
                stringResource(Res.string.your_story),
                fontSize = 26.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Companion.SemiBold,
                modifier = Modifier.Companion.fillMaxWidth(),
            )
        }
        item {
            Spacer(Modifier.Companion.height(20.dp))
        }
        item("gallery") {
            BoxWithConstraints {
                val availableWidth = maxWidth
                val spacing = 5.dp
                val itemWidth = (availableWidth - spacing) / 2

                FlowRow(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                    maxItemsInEachRow = 1,
                    content = {
                        discoversWithDetails.chunked(2).forEachIndexed { index, pair ->
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    5.dp
                                )
                            ) {
                                DiscoverItem(
                                    modifier = Modifier.height(200.dp)
                                        .width(if (pair.size == 1) itemWidth * 2 else itemWidth),
                                    discover = pair[0].discover,
                                    viewHistory = { onOpenDiscover(pair[0]) },
                                    location = pair[0].location,
                                )
                                if (pair.size > 1) {
                                    DiscoverItem(
                                        modifier = Modifier.height(200.dp)
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
        item {
            Spacer(Modifier.height(140.dp))
        }
    }
}