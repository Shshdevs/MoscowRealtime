package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.ui.Content.SeeYouNextTimeContent
import org.koin.compose.koinInject

@Composable
fun SeeYouNextTimeScreen(
    navigator: Navigator = koinInject()
) {
    SeeYouNextTimeContent {navigator.navigateLogOut()}
}