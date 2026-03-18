package com.hotelka.moscowrealtime.presentation.navigation.graphs

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.screen.AuthorizationPage
import com.hotelka.moscowrealtime.presentation.ui.screen.SeeYouNextTimeScreen
import com.hotelka.moscowrealtime.presentation.ui.screen.SignInPage
import com.hotelka.moscowrealtime.presentation.ui.screen.SignUpPage
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authGraph(navController: NavController, menuHandler: MenuHandler, ) {
    navigation(
        route = Destination.AuthGraph.route,
        startDestination = Destination.Authorization.route
    ){
        composable(Destination.Authorization.route) { backStackEntry ->
            menuHandler.updateMenuVisible(false)
            val parentEntry = navController.getBackStackEntry(Destination.RootGraph.route)
            val authViewModel: AuthViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            AuthorizationPage(authViewModel)
        }

        composable(Destination.SignIn.route) {
            menuHandler.updateMenuVisible(false)
            val parentEntry = navController.getBackStackEntry(Destination.RootGraph.route)
            val authViewModel: AuthViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            SignInPage(authViewModel)
        }

        composable(Destination.SignUp.route) {
            menuHandler.updateMenuVisible(false)
            val parentEntry = navController.getBackStackEntry(Destination.RootGraph.route)
            val authViewModel: AuthViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            SignUpPage(authViewModel)
        }

        composable(
            Destination.SeeYouNextTime.route,
            enterTransition = { slideInHorizontally { it / 2 } },
            exitTransition = { slideOutHorizontally { it / 2 } }) {
            menuHandler.updateMenuVisible(false)
            SeeYouNextTimeScreen()
        }
    }
}