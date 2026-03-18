package com.hotelka.moscowrealtime.domain.usecase.scores

import com.hotelka.moscowrealtime.domain.model.WeeklyScore
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow


class GetWeeklyTopUsersUseCase(
    private val repository: ScoreRepository
) {
    suspend operator fun invoke(limit: Int = 10): Flow<Result<List<WeeklyScore>>> {
        return repository.observeWeeklyTopUsers(limit)
    }
}