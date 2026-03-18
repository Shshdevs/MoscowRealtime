package com.hotelka.moscowrealtime.domain.repository

interface UploadPhotoRepository {
    suspend fun uploadPhoto(bucket: String, byteArray: ByteArray): Result<String>
}