package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.PanoramicMoscow
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.AuthPageBottomHeader
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel

@Composable
fun AuthorizationPage(
    authViewModel: AuthViewModel
) {
    Box(Modifier.fillMaxSize()) {
        PanoramicMoscow(
            imagesModifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.8f),
            circlesPaddingBottom = 75.dp
        )
        AuthPageBottomHeader(
            Modifier
                .align(Alignment.BottomStart),
            authViewModel::onEvent
        )
    }
}