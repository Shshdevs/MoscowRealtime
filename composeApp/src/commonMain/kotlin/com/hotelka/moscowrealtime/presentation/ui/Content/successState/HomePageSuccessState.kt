package com.hotelka.moscowrealtime.presentation.ui.Content.successState

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.HomePageEvents
import com.hotelka.moscowrealtime.presentation.model.HomePageModel
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.NearYouWidget
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.HomeHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.EventItem
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import kotlinx.coroutines.launch
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_top
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomePageSuccessState(
    homePageModel: HomePageModel,
    onEvent: (HomePageEvents) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val showButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 3
        }
    }

    val scope = rememberCoroutineScope()

    Box {
        LazyColumn(
            Modifier.fillMaxSize().padding(10.dp),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Spacer(Modifier.height(0.dp))
            }

            item {
                HomeHeader(homePageModel.greeting)
            }

            item {
                NearYouWidget(
                    homePageModel.closestLocation,
                    navigateMap = { onEvent(HomePageEvents.OnNavigateClosestLocationMap) }
                )
            }

            if (homePageModel.events is EventListState.Success) {
                items(homePageModel.events.events, key = { it.id }) { event ->
                    EventItem(event = event) {
                        onEvent(HomePageEvents.OnNavigateEvent(event.id))
                    }
                }
            }

            item {
                Spacer(Modifier.height(200.dp))
            }
        }

        AnimatedVisibility(
            modifier = Modifier.padding(20.dp).padding(bottom = 80.dp).align(Alignment.BottomEnd),
            visible = showButton,
            enter = scaleIn(tween(500)),
            exit = scaleOut(tween(500))
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                },
                shape = CircleShape,
                containerColor = blue,
                contentColor = background,
                elevation = FloatingActionButtonDefaults.elevation(5.dp),
                content = {
                    Icon(
                        painterResource(Res.drawable.arrow_top),
                        contentDescription = "Scroll to top",
                    )
                }
            )
        }
    }
}