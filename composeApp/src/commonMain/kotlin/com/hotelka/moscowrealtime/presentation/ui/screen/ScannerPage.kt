package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.Content.ScannerPageContent

@Composable
fun ScannerPage(menuHandler: MenuHandler){
    ScannerPageContent(
        menuHandler = menuHandler,
        navigateBack = {menuHandler.navigateBack()}
    )
}