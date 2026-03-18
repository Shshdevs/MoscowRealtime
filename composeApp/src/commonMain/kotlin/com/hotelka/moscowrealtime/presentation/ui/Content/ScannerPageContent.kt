package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.MenuRowCamButton
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.CameraFrame
import com.hotelka.moscowrealtime.presentation.ui.Custom.verticalGradient
import com.hotelka.moscowrealtime.presentation.ui.screen.DiscoverPage
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.camera
import moscowrealtime.composeapp.generated.resources.gallery
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScannerPageContent(
    menuHandler: MenuHandler,
    navigateBack: () -> Unit,
) {
    var recognizedDiscover by remember { mutableStateOf<DiscoverDetailed?>(null) }
    CameraFrame(
        onRecognized = { discover, resetRequestState ->
            recognizedDiscover = discover; resetRequestState()
        },
        menuVisible = false,
        navigateBack = navigateBack,
        menuContent = { pickFromGallery, takePic ->
            Box(
                Modifier.align(Alignment.BottomStart).fillMaxWidth().fillMaxHeight(0.3f)
                    .verticalGradient(listOf(Transparent, Black.copy(0.3f)))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart)
                        .padding(bottom = 130.dp).padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = pickFromGallery,
                        modifier = Modifier,
                    ) {
                        Icon(
                            painterResource(Res.drawable.gallery),
                            tint = blue,
                            contentDescription = "Open Gallery"
                        )
                    }

                    MenuRowCamButton(
                        modifier = Modifier.height(80.dp),
                        isOnScannerRoute = true,
                        scannerActive = true,
                        icon = Res.drawable.camera,
                        onClick = takePic
                    )

                    IconButton(
                        onClick = {},
                        modifier = Modifier,
                        enabled = false
                    ) {}
                }
            }
        },
    )
    SlideVerticallyCardAnim(
        visible = recognizedDiscover != null,
        content = {
            recognizedDiscover?.let {
                DiscoverPage(
                    it,
                    onDismiss = {
                        recognizedDiscover = null
                        menuHandler.updateMenuVisible(true)
                    }
                )
            }
        }
    )

}