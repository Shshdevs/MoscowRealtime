package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import kotlinx.coroutines.coroutineScope

class GetCurrentUserOrganizationIdUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String?{
        return coroutineScope {
            authRepository.getCurrentUserId()?.let {
               userRepository.getUser(it).getOrNull()?.organizationId
            }
        }
    }
}