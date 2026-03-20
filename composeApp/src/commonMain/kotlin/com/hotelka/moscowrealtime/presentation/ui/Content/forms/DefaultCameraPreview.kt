package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.PermissionsCard
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.enums.CameraLens
import com.kashif.cameraK.enums.Directory
import com.kashif.cameraK.enums.FlashMode
import com.kashif.cameraK.enums.ImageFormat
import com.kashif.cameraK.enums.QualityPrioritization
import com.kashif.cameraK.permissions.providePermissions
import com.kashif.cameraK.ui.CameraPreview
import com.kashif.imagesaverplugin.ImageSaverConfig
import com.kashif.imagesaverplugin.rememberImageSaverPlugin
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.camera_permission
import moscowrealtime.composeapp.generated.resources.storage_permission

@Composable
fun DefaultCameraPreview(
    onCameraControllerReady: (CameraController) -> Unit,
    navigateBack:() -> Unit
){
    if (!handleCameraPermissions()) {
        SlideVerticallyCardAnim(
            visible = true,
            content = {
                PermissionsCard(
                    permission = Res.string.camera_permission,
                    onDismiss = {
                        navigateBack()
                    }
                )
            }
        )
    } else if (!handleStoragePermissions()) {
        SlideVerticallyCardAnim(
            visible = true,
            content = {
                PermissionsCard(
                    permission = Res.string.storage_permission,
                    onDismiss = { navigateBack() }
                )
            }
        )
    } else {
        val imageSaverPlugin = rememberImageSaverPlugin(ImageSaverConfig(false))
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            cameraConfiguration = {
                setQualityPrioritization(QualityPrioritization.QUALITY)
                setCameraLens(CameraLens.BACK)
                setFlashMode(FlashMode.OFF)
                setImageFormat(ImageFormat.PNG)
                setDirectory(Directory.PICTURES)
                addPlugin(imageSaverPlugin)
            },
            onCameraControllerReady = onCameraControllerReady
        )
    }
}

@Composable
fun handleCameraPermissions(): Boolean{
    val permissions = providePermissions()
    val cameraPermissionState = remember { mutableStateOf(permissions.hasCameraPermission()) }
    if (!cameraPermissionState.value) {
        permissions.RequestCameraPermission(
            onGranted = {cameraPermissionState.value = true},
            onDenied = { cameraPermissionState.value = false}
        )
        return cameraPermissionState.value
    } else {
        return cameraPermissionState.value
    }
}

@Composable
fun handleStoragePermissions(): Boolean {
    val permissions = providePermissions()

    val storagePermissionState = remember { mutableStateOf(permissions.hasStoragePermission()) }
    if (!storagePermissionState.value) {
        permissions.RequestStoragePermission(
            onGranted = {storagePermissionState.value = true},
            onDenied = { storagePermissionState.value = false;}
        )
        return storagePermissionState.value
    } else {
        return storagePermissionState.value
    }
}