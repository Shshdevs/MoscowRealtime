package com.hotelka.moscowrealtime.presentation.ui.Content.descriptions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.MiniCardBriefInfo
import com.hotelka.moscowrealtime.presentation.utils.DateFormatters.toDate
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.bookmark
import moscowrealtime.composeapp.generated.resources.circle_user
import moscowrealtime.composeapp.generated.resources.not_found
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscoverDescription(
    lazyListState: LazyListState,
    discoverDetailed: DiscoverDetailed,
    ) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        item("brief_info_row") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MiniCardBriefInfo(
                    Modifier,
                    icon = Res.drawable.circle_user,
                    info = discoverDetailed.userAuthor?.username?.let { "@${it}" }
                        ?: stringResource(Res.string.not_found)
                )
                MiniCardBriefInfo(
                    Modifier,
                    icon = Res.drawable.bookmark,
                    info = discoverDetailed.discover.timestamp.toDate()
                )
            }
            Spacer(Modifier.height(20.dp))
        }
        item {
            Text(
                discoverDetailed.discover.yandexGPTGuide,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
        item("just_spacer") {
            Spacer(Modifier.height(200.dp))
        }
    }
}