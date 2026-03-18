package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import com.hotelka.moscowrealtime.presentation.ui.Content.items.CirclesProgressItem
import kotlinx.coroutines.delay
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.discover_new
import moscowrealtime.composeapp.generated.resources.moscow_city
import moscowrealtime.composeapp.generated.resources.moscow_evening
import moscowrealtime.composeapp.generated.resources.moscow_red_flat
import moscowrealtime.composeapp.generated.resources.moscow_river
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PanoramicMoscow(imagesModifier: Modifier, circlesPaddingBottom: Dp) {

    val images = listOf(
        Res.drawable.moscow_city,
        Res.drawable.moscow_evening,
        Res.drawable.moscow_river,
        Res.drawable.moscow_red_flat
    )
    val pagerState = rememberPagerState(0) { images.size }

    LaunchedEffect(true) {
        while (true) {
            delay(4000)
            pagerState.animateScrollToPage(
                if (pagerState.currentPage != images.size - 1) pagerState.currentPage + 1
                else 0
            )
        }
    }
    Box(Modifier.fillMaxSize()){
        HorizontalPager(
            modifier = imagesModifier
                , state = pagerState,
            userScrollEnabled = false
        ) { currentPage ->

            Image(
                painterResource(images[currentPage]),
                contentDescription = "Moscow",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(darkBlue.copy(0.5f))
        ) {
            CirclesProgressItem(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = circlesPaddingBottom)
                    .padding(30.dp),
                10.dp,
                images.size,
                pagerState.currentPage,
            )
            Text(
                stringResource(Res.string.discover_new),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(30.dp)
                    .fillMaxWidth(0.9f),
                fontSize = 30.sp,
                color = background,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}