package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.ScannerPage
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler


fun NavGraphBuilder.scannerGraph(menuHandler: MenuHandler) {
    composable(Destination.CameraScanner.route) {
        ScannerPage(menuHandler)
    }
}