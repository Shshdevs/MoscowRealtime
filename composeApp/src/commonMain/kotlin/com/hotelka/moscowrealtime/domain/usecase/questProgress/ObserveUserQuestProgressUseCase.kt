package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ObserveUserQuestProgressUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val questProgressRepository: QuestProgressRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Result<QuestProgress?>> = flow {
        val currUserId = authRepository.getCurrentUserId()
        if (currUserId == null) {
            emit(Result.failure(UserNotAuthenticatedException()))
            return@flow
        }

        emitAll(
            userRepository.observeUser(currUserId).flatMapLatest { userResult ->
                userResult.fold(
                    onSuccess = { user ->
                        val questProgressId = user.currentQuestProgress
                        if (questProgressId == null) {
                            flowOf(Result.success(null))
                        } else {
                            questProgressRepository.observeQuestProgress(questProgressId)
                        }
                    },
                    onFailure = {
                        flowOf(Result.success(null))
                    }
                )
            }
        )
    }
}
