package com.hotelka.moscowrealtime.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.core.config.AppConfig
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.AppNavHost
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.MenuRowCamButton
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.MenuRow
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.WifiCard
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.sunildhiman90.kmauth.core.KMAuthConfig
import com.sunildhiman90.kmauth.core.KMAuthInitializer
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.scan
import org.koin.compose.koinInject


@Composable
fun App() {
    KMAuthInitializer.initialize(KMAuthConfig.forGoogle(AppConfig.webClientId))

    val menuHandler: MenuHandler = koinInject()
    val menuState by menuHandler.menuUiState.collectAsState()

    Scaffold(
        containerColor = background,
        topBar = { Box(Modifier.fillMaxWidth()) }
    ) {
        Box {
            AppNavHost(Modifier.fillMaxSize(), menuHandler)
            MenuRow(Modifier.align(Alignment.BottomCenter))

            if (menuState.isMenuVisible) {
                MenuRowCamButton(
                    modifier = Modifier
                        .padding(bottom = 45.dp)
                        .align(Alignment.BottomCenter)
                        .height(56.dp),
                    isOnScannerRoute = false,
                    scannerActive = menuState.isScannerActive,
                    icon = Res.drawable.scan,
                ) { menuHandler.navigate(Destination.CameraScanner.route) }
            }

            WifiCard(Modifier.fillMaxWidth().padding(20.dp))
        }
    }
}