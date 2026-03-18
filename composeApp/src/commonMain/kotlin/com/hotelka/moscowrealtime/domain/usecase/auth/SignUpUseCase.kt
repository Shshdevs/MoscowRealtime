package com.hotelka.moscowrealtime.domain.usecase.auth

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import kotlin.coroutines.cancellation.CancellationException

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        username: String
    ): Result<Unit> {
        return try {
            val authResult = authRepository.signUp(email, password)

            if (authResult.isFailure) {
                return Result.failure(authResult.exceptionOrNull()
                    ?: Exception("Unknown authentication error"))
            }

            val firebaseUser = authResult.getOrNull()
            if (firebaseUser == null) {
                return Result.failure(Exception("Failed to get user after sign up"))
            }

            val user = User(
                id = firebaseUser.uid,
                name = name,
                username = username
            )

            val createResult = userRepository.createUser(user)

            if (createResult.isSuccess) {
                Result.success(Unit)
            } else {
                try {
                    authRepository.deleteUser()
                } catch (_: Exception) {
                    Logger.e("Failed to delete auth user after profile creation failure")
                }

                Result.failure(createResult.exceptionOrNull()
                    ?: Exception("Failed to create user profile"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}