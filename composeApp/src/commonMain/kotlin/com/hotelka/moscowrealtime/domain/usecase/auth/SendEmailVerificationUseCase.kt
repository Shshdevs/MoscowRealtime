package com.hotelka.moscowrealtime.domain.usecase.auth

import com.hotelka.moscowrealtime.domain.repository.AuthRepository

class SendEmailVerificationUseCase (
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        return repository.sendEmailVerification()
    }
}