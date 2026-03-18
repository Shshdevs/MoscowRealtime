package com.hotelka.moscowrealtime.domain.model

import com.hotelka.moscowrealtime.presentation.navigation.Destination
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.history
import moscowrealtime.composeapp.generated.resources.home
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.profile
import moscowrealtime.composeapp.generated.resources.quests
import moscowrealtime.composeapp.generated.resources.scan
import moscowrealtime.composeapp.generated.resources.scanner
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class NavItem(val label: StringResource, val iconResource: DrawableResource, val route: String) {
    object Home: NavItem(Res.string.home, Res.drawable.home, Destination.Home.route)
    object History:  NavItem(Res.string.history, Res.drawable.history, Destination.Discovers.route)
    object Camera:  NavItem(Res.string.scanner, Res.drawable.scan, Destination.CameraScanner.route)
    object Profile:  NavItem(Res.string.profile, Res.drawable.profile, Destination.CurrentUserProfile.route)
    object Quests:  NavItem(Res.string.quests, Res.drawable.location, Destination.Quests.route)
}
