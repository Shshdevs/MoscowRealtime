package com.hotelka.moscowrealtime.domain.usecase.auth

import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class ObserveAuthStateUseCase (
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<FirebaseUser?> {
        return repository.observeAuthState()
    }
}