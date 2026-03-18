package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.TopWeeklyUser
import com.hotelka.moscowrealtime.presentation.utils.currentTimestamp

data class TopWeeklyUsersUiModel(
    val topUsers: List<TopWeeklyUser>,
    val firstScoreTimestamp: Long = topUsers.minOfOrNull { it.score.firstScoreTimestamp } ?: currentTimestamp(),
    val lastScoreTimestamp: Long = topUsers.maxOfOrNull { it.score.lastScoreTimestamp } ?: currentTimestamp(),
)
