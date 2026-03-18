package com.hotelka.moscowrealtime.presentation.navigation

import androidx.navigation.NavHostController

class Navigator {

    lateinit var navController: NavHostController

    fun navigateMainGraph() {
        navController.navigate(Destination.MainGraph.route) {
            popUpTo(Destination.RootGraph.route) { inclusive = true }
        }
    }

    fun navigate(route: String, popUpTo: String? = null, inclusive: Boolean = false) {
        if (navController.currentDestination?.route != route) {
            navController.navigate(route) {
                popUpTo?.let {
                    popUpTo(it) { this.inclusive = inclusive }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun seeYouNextTime() {
        navController.popBackStack(0, true)
        navController.navigate(Destination.AuthGraph.route) {
            launchSingleTop = true
        }
        navController.navigate(Destination.SeeYouNextTime.route)
    }

    fun navigateLogOut() {
        navController.navigate(Destination.AuthGraph.route) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    fun back() {
        navController.popBackStack()
    }
}