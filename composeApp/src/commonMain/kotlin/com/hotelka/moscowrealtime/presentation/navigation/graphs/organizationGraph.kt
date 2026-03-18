package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.GroupPage
import com.hotelka.moscowrealtime.presentation.ui.screen.GroupsParticipantPage
import com.hotelka.moscowrealtime.presentation.ui.screen.OrganizationHomePage
import com.hotelka.moscowrealtime.presentation.ui.screen.OrganizerPage
import com.hotelka.moscowrealtime.presentation.viewmodel.OrganizationGraphViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.organizationGraph(
    navController: NavController,
    menuHandler: MenuHandler
) {
    navigation(
        route = Destination.OrganizationGraph.route,
        startDestination = Destination.OrganizationPage.route
    ) {
        composable(Destination.OrganizationPage.route) { backStackEntry ->
            menuHandler.updateMenuVisible(false)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.OrganizationGraph.route) }
            val orgGraphViewModel: OrganizationGraphViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            OrganizationHomePage(orgGraphViewModel)
        }

        composable(Destination.OrganizerPage.route) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.OrganizationGraph.route) }
            val orgGraphViewModel: OrganizationGraphViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)
            OrganizerPage(orgGraphViewModel)
        }
        composable(Destination.GroupPage.route) { backStackEntry ->
            menuHandler.updateMenuVisible(false)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.OrganizationGraph.route) }
            val orgGraphViewModel: OrganizationGraphViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)
            GroupPage(viewModel = orgGraphViewModel)
        }

        composable(Destination.ClientGroups.route) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.OrganizationGraph.route) }
            val orgGraphViewModel: OrganizationGraphViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            GroupsParticipantPage(orgGraphViewModel)
        }
    }
}
