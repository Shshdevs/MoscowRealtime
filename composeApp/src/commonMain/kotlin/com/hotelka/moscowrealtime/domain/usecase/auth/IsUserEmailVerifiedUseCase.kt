package com.hotelka.moscowrealtime.domain.usecase.auth

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.repository.AuthRepository

class IsUserEmailVerifiedUseCase (
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean? {
        val verified = repository.isUserEmailVerified()
        Logger.withTag("Email verified").d { verified.toString() }
        return verified
    }
}