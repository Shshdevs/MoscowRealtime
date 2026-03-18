package com.hotelka.moscowrealtime.domain.usecase.mrtApi

import com.hotelka.moscowrealtime.domain.exceptions.MrtApiExceptionUnrecognized
import com.hotelka.moscowrealtime.domain.exceptions.MrtApiExceptions
import com.hotelka.moscowrealtime.domain.exceptions.PhotoUploadException
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.api.MRTApiResult
import com.hotelka.moscowrealtime.domain.repository.MRTApiRepository
import com.hotelka.moscowrealtime.domain.repository.UploadPhotoRepository

class AnalyzeImageUseCase(
    private val repository: MRTApiRepository,
    private val uploadPhotoRepository: UploadPhotoRepository
) {
    suspend operator fun invoke(userId: String, byteArray: ByteArray): MRTApiResult<Discover> =
        try {
            val filename = uploadPhotoRepository.uploadPhoto("analyzed-images", byteArray)
                .getOrElse { error ->
                    return MRTApiResult.failure(PhotoUploadException(error))
                }
            val result = repository.analyzeImage(filename, userId)
            if (result.isSuccess) result
            else {
                val error = result.error
                if (result.error?.message?.contains("Unrecognized") == true) {
                    MRTApiResult.failure(MrtApiExceptionUnrecognized())
                } else {
                    MRTApiResult.failure(
                        MrtApiExceptions(
                            error ?: Exception("Unknown error")
                        )
                    )
                }
            }
        } catch (e: Exception) {
            MRTApiResult.failure(MrtApiExceptions(e))
        }
}