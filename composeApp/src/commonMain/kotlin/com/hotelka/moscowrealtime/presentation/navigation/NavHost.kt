package com.hotelka.moscowrealtime.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.hotelka.moscowrealtime.domain.usecase.auth.IsUserEmailVerifiedUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.IsUserLoggedInUseCase
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.graphs.authGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.eventGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.mainGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.organizationGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.questConstructorGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.questGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.questProgressGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.scannerGraph
import com.hotelka.moscowrealtime.presentation.navigation.graphs.userGraph
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier,
    menuHandler: MenuHandler,
    isUserLoggedInUseCase: IsUserLoggedInUseCase = koinInject(),
    isUserEmailVerifiedUseCase: IsUserEmailVerifiedUseCase = koinInject(),
    navigator: Navigator = koinInject()
) {
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        navigator.navController = navController
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        menuHandler.updateMenuForRoute(currentRoute)
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.RootGraph.route
    ) {
        navigation(
            route = Destination.RootGraph.route,
            startDestination = if (isUserLoggedInUseCase()) Destination.MainGraph.route else Destination.AuthGraph.route
        ) {
            mainGraph(
                startDestination = if (isUserEmailVerifiedUseCase() ?: false)
                    Destination.Home.route else Destination.EmailVerification.route,
                navController,
                menuHandler
            )
            authGraph(navController, menuHandler)
            eventGraph(navController, menuHandler)
            scannerGraph(menuHandler)
            questProgressGraph(navController, menuHandler)
            organizationGraph(navController, menuHandler)
            userGraph(menuHandler)
            questGraph(menuHandler)
            questConstructorGraph(menuHandler)
        }
    }
}