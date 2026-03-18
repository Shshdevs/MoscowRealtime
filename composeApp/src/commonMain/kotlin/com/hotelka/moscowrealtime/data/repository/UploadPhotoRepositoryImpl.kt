package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.UploadPhotoDataSource
import com.hotelka.moscowrealtime.domain.repository.UploadPhotoRepository

class UploadPhotoRepositoryImpl(
    private val uploadPhotoDataSource: UploadPhotoDataSource
) : UploadPhotoRepository {
    override suspend fun uploadPhoto(bucket: String, byteArray: ByteArray): Result<String> {
        return uploadPhotoDataSource.uploadPhoto(bucket, byteArray)
    }
}