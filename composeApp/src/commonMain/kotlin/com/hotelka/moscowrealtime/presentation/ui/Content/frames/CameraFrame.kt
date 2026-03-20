package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.DefaultCameraPreview
import com.hotelka.moscowrealtime.presentation.viewmodel.AnalyzeImageViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CameraFrame(
    onRecognized: @Composable ColumnScope.(DiscoverDetailed, () -> Unit) -> Unit,
    menuContent: @Composable BoxScope.(() -> Unit, () -> Unit) -> Unit,
    navigateBack: () -> Unit,
    menuVisible: Boolean = false,
    menuHandler: MenuHandler = koinInject(),
    analyzeImageViewModel: AnalyzeImageViewModel = koinViewModel(),
) {
    val requestState by analyzeImageViewModel.requestState.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        DefaultCameraPreview(
            { cameraController -> analyzeImageViewModel.setCameraController(cameraController) },
            navigateBack
        )
        menuContent(
            { analyzeImageViewModel.pickFromGallery{menuHandler.updateMenuVisible(menuVisible)}; menuHandler.updateMenuVisible(false) },
            { analyzeImageViewModel.takePic(); menuHandler.updateMenuVisible(false) }
        )
        RequestFrame(
            modifier = Modifier.padding(top = 40.dp).fillMaxSize(),
            requestState = requestState,
            onRecognized = { onRecognized(it) { analyzeImageViewModel.resetRequestState() }},
            onDismissRequest = { analyzeImageViewModel.resetRequestState(); menuHandler.updateMenuVisible(menuVisible) },
        )
    }
}