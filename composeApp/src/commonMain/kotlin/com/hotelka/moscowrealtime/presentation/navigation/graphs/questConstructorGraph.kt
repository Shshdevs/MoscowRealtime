package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.QuestConstructorScreen
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler

fun NavGraphBuilder.questConstructorGraph(menuHandler: MenuHandler) {
    navigation(
        route = Destination.QuestConstructorGraph.route,
        startDestination = Destination.QuestConstructorPage.route,
    ) {
        composable(Destination.QuestConstructorPage.route) {
            menuHandler.updateMenuVisible(false)
            QuestConstructorScreen()
        }
    }
}