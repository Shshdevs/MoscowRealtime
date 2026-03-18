package com.hotelka.moscowrealtime.presentation.controllers

import com.hotelka.moscowrealtime.domain.model.NavItem
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.menuState.MenuState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MenuHandler(
    private val navigator: Navigator,
) {
    private val _menuUiState: MutableStateFlow<MenuState> = MutableStateFlow(
        MenuState(
            items = listOf(
                NavItem.Home,
                NavItem.History,
                NavItem.Camera,
                NavItem.Quests,
                NavItem.Profile
            )
        )
    )
    val menuUiState: StateFlow<MenuState> = _menuUiState.asStateFlow()

    fun navigateBack() {
        navigator.back()
    }

    fun navigate(route: String) {
        navigator.navigate(route)
    }

    fun updateMenuVisible(isVisible: Boolean) {
        _menuUiState.update { menuState ->
            menuState.copy(isMenuVisible = isVisible)
        }
    }

    fun updateMenuForRoute(route: String?) {
        val navItems = menuUiState.value.items
        val isScannerRoute = route == Destination.CameraScanner.route

        val newIndex = when {
            isScannerRoute -> -1
            navItems.any { it.route == route } -> navItems.indexOfFirst { it.route == route }
            else -> menuUiState.value.selectedIndex
        }

        _menuUiState.update { old ->
            old.copy(
                selectedIndex = newIndex,
                isScannerActive = isScannerRoute
            )
        }
    }
}