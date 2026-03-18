package com.hotelka.moscowrealtime.data.remote

interface UploadPhotoDataSource {
    suspend fun uploadPhoto(bucket: String, byteArray: ByteArray):  Result<String>
}