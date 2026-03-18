package com.hotelka.moscowrealtime.domain.usecase.auth

import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseUser

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> {
        return authRepository.signIn(email, password)
    }
}