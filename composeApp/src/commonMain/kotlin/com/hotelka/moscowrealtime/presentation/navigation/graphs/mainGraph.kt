package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.CurrUserProfile
import com.hotelka.moscowrealtime.presentation.ui.screen.DiscoversPage
import com.hotelka.moscowrealtime.presentation.ui.screen.FindFriendsPage
import com.hotelka.moscowrealtime.presentation.ui.screen.HomePage
import com.hotelka.moscowrealtime.presentation.ui.screen.InboxPage
import com.hotelka.moscowrealtime.presentation.ui.screen.LibraryPage
import com.hotelka.moscowrealtime.presentation.ui.screen.QuestsPage
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.CurrentUserProfileViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.HistoryPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.HomeViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.LibraryViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestsPageViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.mainGraph(
    startDestination: String,
    navController: NavController,
    menuHandler: MenuHandler
) {
    navigation(
        route = Destination.MainGraph.route,
        startDestination = startDestination
    ) {
        composable(
            Destination.Home.route,
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(true)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.MainGraph.route) }
            val viewModel: HomeViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)
            HomePage(homeViewModel = viewModel)
        }
        composable(
            Destination.Discovers.route,
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(true)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.MainGraph.route) }
            val historyPageViewModel: HistoryPageViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            DiscoversPage(historyPageViewModel = historyPageViewModel)
        }

        composable(
            Destination.Quests.route,
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(true)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.MainGraph.route) }
            val questsPageViewModel: QuestsPageViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            QuestsPage(
                questsPageViewModel = questsPageViewModel,
            )
        }

        composable(
            Destination.QuestsLibrary.route,
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(true)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.MainGraph.route) }
            val libraryViewModel: LibraryViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)
            LibraryPage(libraryViewModel)
        }

        composable(
            Destination.CurrentUserProfile.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "hotelka_mrt://curr_user_profile" }
            ),
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }
        ) { backStackEntry ->
            menuHandler.updateMenuVisible(true)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.MainGraph.route) }
            val userProfileViewModel: CurrentUserProfileViewModel =
                koinViewModel(viewModelStoreOwner = parentEntry)

            CurrUserProfile(userProfileViewModel)
        }
        composable(Destination.SearchUsersPage.route) {
            FindFriendsPage()
        }
        composable(
            Destination.EmailVerification.route,
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }) { backStackEntry ->
            menuHandler.updateMenuVisible(false)
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Destination.RootGraph.route) }
            val authViewModel: AuthViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            InboxPage(authViewModel)
        }
    }
}
