package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.presentation.extensions.getRandomString
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage

class UploadPhotoDataSourceImpl(
    private val supabase: SupabaseClient
): UploadPhotoDataSource {

    override suspend fun uploadPhoto(bucket: String, byteArray: ByteArray): Result<String> = try {
        val fileName = getRandomString(12)+".jpg"
        val bucketApi = supabase.storage[bucket]
        Result.success( bucketApi.upload(fileName, byteArray).path)
    } catch (e: Exception){
        Result.failure(Exception("Error Uploading file", e))
    }
}