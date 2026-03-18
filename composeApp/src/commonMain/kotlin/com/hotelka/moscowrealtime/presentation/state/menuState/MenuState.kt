package com.hotelka.moscowrealtime.presentation.state.menuState

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.NavItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.camera
import moscowrealtime.composeapp.generated.resources.scan

data class MenuState(
    val selectedIndex: Int = 0,
    val isScannerActive: Boolean = false,
    val isMenuVisible: Boolean = true,
    val items: List<NavItem> = listOf()
)
