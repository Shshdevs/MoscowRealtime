package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.CurrQuestInteractiveMapPage
import com.hotelka.moscowrealtime.presentation.ui.screen.QuestProgressPage
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestProgressGraphViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.questProgressGraph(navController: NavController, menuHandler: MenuHandler) {
    navigation(
        route = Destination.QuestProgressGraph.route,
        startDestination = Destination.CurrQuestProgressPage.route
    ) {
        composable(Destination.CurrQuestProgressPage.route) { backStackEntry ->
            menuHandler.updateMenuVisible(false)

            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.QuestProgressGraph.route) }
            val questProgressGraphViewModel: QuestProgressGraphViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            QuestProgressPage(questProgressGraphViewModel)
        }

        composable(
            route = Destination.CurrQuestInteractiveMap.route,
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(false)

            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.QuestProgressGraph.route) }
            val questProgressGraphViewModel: QuestProgressGraphViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            CurrQuestInteractiveMapPage(questProgressGraphViewModel)
        }
    }
}
