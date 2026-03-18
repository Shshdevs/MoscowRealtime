package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.core.config.AppConfig
import com.hotelka.moscowrealtime.domain.exceptions.PhotoUploadException
import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.exceptions.UserRetrievalException
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.UploadPhotoRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class UpdateUserProfilePhotoUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val uploadPhotoRepository: UploadPhotoRepository
) {
    suspend operator fun invoke(byteArray: ByteArray): Result<Unit> {
        val userId = authRepository.getCurrentUserId() ?: return Result.failure(
            UserNotAuthenticatedException()
        )
        return runCatching {
            val filename = uploadPhotoRepository.uploadPhoto("users", byteArray)
                .getOrElse { error ->
                    return Result.failure(PhotoUploadException(error))
                }
            val url = AppConfig.supabaseURL+"/storage/v1/object/public/users/" + filename
            val user = userRepository.getUser(userId)
                .getOrElse { error ->
                    return Result.failure(UserRetrievalException(error))
                }
            userRepository.updateUser(user.copy(userPic = url))
        }
    }
}