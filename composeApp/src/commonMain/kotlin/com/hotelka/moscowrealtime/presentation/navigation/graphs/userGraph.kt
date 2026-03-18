package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.UserProfileScreen
import com.hotelka.moscowrealtime.presentation.viewmodel.UserProfilePageViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.userGraph(menuHandler: MenuHandler) {
    composable(
        route = Destination.UserProfilePage.route,
        arguments = listOf(navArgument("userId") { type = NavType.StringType }),
        deepLinks = listOf(
            navDeepLink { uriPattern = "hotelka_mrt://profile/{userId}" }
        )
    ) { backStackEntry ->
        menuHandler.updateMenuVisible(true)
        val viewModel: UserProfilePageViewModel = koinViewModel(
            viewModelStoreOwner = backStackEntry
        )
        UserProfileScreen(viewModel)
    }
}