package com.hotelka.moscowrealtime.domain.usecase.auth

import com.hotelka.moscowrealtime.domain.repository.AuthRepository

class SendChangePasswordLinkUseCase (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) {
        return repository.sendChangePasswordLink(email)
    }
}