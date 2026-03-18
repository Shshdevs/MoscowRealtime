package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TabbedPager(
    tabsLabels: List<Pair<DrawableResource, StringResource>>,
    userScrollEnabled: Boolean = true,
    pagesContent: @Composable (ColumnScope.(Int) -> Unit)
) {
    val pageCount = tabsLabels.count()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }

    if (pageCount == 0) {
        return
    }

    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        SecondaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            containerColor = background,
            contentColor = blue,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(pagerState.currentPage),
                    height = 2.dp,
                    color = background
                )
            },
            divider = {
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    2.dp,
                    blue.copy(0.2f)
                )
            }
        ) {
            tabsLabels.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    selectedContentColor = blue,
                    unselectedContentColor = blue.copy(0.5f),
                    text = { Text(stringResource(tab.second)) },
                    icon = { Icon(painterResource(tab.first), contentDescription = null) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = userScrollEnabled,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Column(Modifier.fillMaxSize().padding(10.dp)) {
                pagesContent(page)
            }
        }
    }
}