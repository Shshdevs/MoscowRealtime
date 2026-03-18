package com.hotelka.moscowrealtime.domain.usecase.auth

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import com.hotelka.moscowrealtime.presentation.extensions.cyrillicToLatinic
import com.hotelka.moscowrealtime.presentation.extensions.getRandomString
import dev.gitlive.firebase.auth.FirebaseUser

class GoogleAuthUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(onAuthenticated:() -> Unit): Result<Unit> {
        return authRepository.googleAuth()
            .fold(
                onSuccess = { firebaseUser -> onAuthenticated(); createNewUser(firebaseUser) },
                onFailure = { Result.failure(it) }
            )
    }

    private suspend fun createNewUser(firebaseUser: FirebaseUser): Result<Unit> {
        val baseName = firebaseUser.displayName?.takeIf { it.isNotBlank() }
            ?: "Guest${getRandomString(5)}"

        val baseUsername = baseName.cyrillicToLatinic().lowercase()
        val finalUsername = generateUniqueUsername(baseUsername)

        val user = User(
            id = firebaseUser.uid,
            name = baseName,
            username = finalUsername,
        )

        return userRepository.createUser(user)
    }
    private suspend fun generateUniqueUsername(baseUsername: String): String {
        var username = baseUsername
        var attempts = 0

        while (!userRepository.validateUsername(username).getOrElse { false }) {
            if (attempts >= 5) {
                username = "Guest${getRandomString(8)}"
                break
            }
            username = baseUsername + getRandomString(4)
            attempts++
        }

        return username
    }
}