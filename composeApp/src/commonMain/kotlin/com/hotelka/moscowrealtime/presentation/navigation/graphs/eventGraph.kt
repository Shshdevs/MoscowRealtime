package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.savedstate.read
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.EventMapRoutePage
import com.hotelka.moscowrealtime.presentation.ui.screen.EventPage
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.viewmodel.EventGraphViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.eventGraph(
    navController: NavController,
    menuHandler: MenuHandler,
) {
    navigation(
        route = Destination.EventGraph.route,
        startDestination = Destination.EventPage.route
    ) {
        composable(
            route = Destination.EventPage.route,
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(true)
            Logger.withTag("Event graph")
                .d { navController.currentBackStackEntry?.destination?.route.toString() }

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.EventGraph.route)
            }

            val eventGraphViewModel: EventGraphViewModel = koinViewModel(
                viewModelStoreOwner = parentEntry
            )

            val eventId =
                backStackEntry.arguments?.read { getString("eventId") }?.toIntOrNull() ?: 0
            EventPage(eventId, eventGraphViewModel)
        }

        composable(
            route = Destination.CurrEventMapRoute.route,
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(false)
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.EventGraph.route)
            }

            val eventGraphViewModel: EventGraphViewModel = koinViewModel(
                viewModelStoreOwner = parentEntry
            )

            EventMapRoutePage(eventGraphViewModel)
        }
    }
}