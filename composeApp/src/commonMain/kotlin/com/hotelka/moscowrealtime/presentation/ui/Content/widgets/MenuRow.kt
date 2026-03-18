package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MenuRow(
    modifier: Modifier,
    menuHandler: MenuHandler = koinInject()
) {
    val menuState by menuHandler.menuUiState.collectAsState()
    val menuBackgroundColor by animateColorAsState(
        targetValue = if (menuState.isScannerActive) Transparent else background,
        animationSpec = tween(700, easing = FastOutSlowInEasing)
    )
    val spacerWidth by animateDpAsState(
        targetValue = if (menuState.isScannerActive) 0.dp else 80.dp,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
    )

    if (menuState.isMenuVisible) {
        Box(
            modifier.clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                .background(menuBackgroundColor)
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                menuState.items.forEachIndexed { index, navItem ->
                    if (navItem.route == Destination.CameraScanner.route) {
                        Spacer(Modifier.width(spacerWidth))
                    } else {
                        NavigationBarItem(
                            selected = menuState.selectedIndex == index,
                            onClick = {
                                menuHandler.navigate(navItem.route)
                            },
                            icon = {
                                Icon(
                                    painterResource(navItem.iconResource),
                                    contentDescription = "NavItemIcon",
                                    tint =
                                        if (menuState.isScannerActive) blue
                                        else if (menuState.selectedIndex == index) blue else secondaryTextColor
                                )
                            },
                            modifier = Modifier.fillMaxWidth().weight(1f)
                                .clip(CircleShape),
                            label = {},
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Transparent
                            )
                        )
                    }
                }
            }
        }
    } else {
        Box(Modifier.fillMaxWidth())
    }
}