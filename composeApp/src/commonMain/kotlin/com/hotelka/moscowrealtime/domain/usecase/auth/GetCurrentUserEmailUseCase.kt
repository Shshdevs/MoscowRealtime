package com.hotelka.moscowrealtime.domain.usecase.auth

import com.hotelka.moscowrealtime.domain.repository.AuthRepository

class GetCurrentUserEmailUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): String? {
        return repository.getCurrentUserEmail()
    }
}