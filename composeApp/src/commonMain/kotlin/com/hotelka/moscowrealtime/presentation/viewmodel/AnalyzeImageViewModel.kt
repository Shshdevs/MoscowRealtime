package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.exceptions.MrtApiExceptionUnrecognized
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.mrtApi.AnalyzeImageUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversDetailsUseCase
import com.hotelka.moscowrealtime.presentation.state.RequestState
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.result.ImageCaptureResult
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.compressImage
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnalyzeImageViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val analyzeImageUseCase: AnalyzeImageUseCase,
    private val getDiscoversDetailsUseCase: GetDiscoversDetailsUseCase
) : ViewModel() {
    private val cameraController = MutableStateFlow<CameraController?>(null)
    private val _requestState = MutableStateFlow<RequestState>(RequestState.None)
    val requestState: StateFlow<RequestState> = _requestState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)

    fun takePic() {
        viewModelScope.launch {
            _requestState.value = RequestState.Running
            when (val pic = cameraController.value?.takePicture()) {
                is ImageCaptureResult.Error -> {
                    _errorMessage.value = "Error taking picture"
                }
                is ImageCaptureResult.Success -> {
                    analyzeImage(pic.byteArray)
                }
                null -> {}
            }
        }
    }

    fun pickFromGallery() {
        viewModelScope.launch {
            val platformFile = FileKit.openFilePicker(type = FileKitType.Image)
            if (platformFile == null) {
                return@launch
            }
            analyzeImage(platformFile.readBytes())
        }
    }


    private fun analyzeImage(byteArray: ByteArray) {
        viewModelScope.launch {
            _requestState.value = RequestState.Running
            getCurrentUserIdUseCase()?.let { userId ->
                val byteArray = FileKit.compressImage(byteArray, quality = 60)
                val result = analyzeImageUseCase(userId, byteArray)

                Logger.withTag("AnalyzeImageIOS Debug").d { result.toString() }
                if (result.data?.success == true) {
                    getDiscoversDetailsUseCase(listOf(result.data)).firstOrNull()?.let {
                        _requestState.value =
                            RequestState.Recognized(it)
                    }?:{
                        _requestState.value = RequestState.Error(result.error?.message.toString())
                    }
                } else {
                    if (result.error is MrtApiExceptionUnrecognized) {
                        _requestState.value = RequestState.Unrecognized
                    } else {
                        Logger.withTag("AnalyzeImageIOS Debug").d { result.error.toString() }
                        _requestState.value = RequestState.Error(result.error?.message.toString())
                    }
                }
            }
            return@launch
        }
    }

    fun setCameraController(cameraController: CameraController) {
        this.cameraController.value = cameraController
    }

    fun resetRequestState() {
        _requestState.value = RequestState.None
    }
}

