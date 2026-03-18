package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.QuestPage
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestPageViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.questGraph(menuHandler: MenuHandler) {
    navigation(
        route = Destination.QuestGraph.route,
        startDestination = Destination.QuestPage.route
    ) {
        composable(
            route = Destination.QuestPage.route,
            arguments = listOf(navArgument("questId") { type = NavType.StringType })
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(false)
            val viewModel: QuestPageViewModel = koinViewModel(
                viewModelStoreOwner = backStackEntry
            )
            QuestPage(viewModel)
        }
    }
}