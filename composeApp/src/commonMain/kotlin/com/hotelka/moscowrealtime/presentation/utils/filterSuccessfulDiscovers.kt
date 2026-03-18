package com.hotelka.moscowrealtime.presentation.utils

import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed


fun List<DiscoverDetailed>.filterSuccessfulDiscovers(questProgress: QuestProgress): List<DiscoverDetailed> {
    return this.filter { discover ->
        val verificationResult =
            questProgress.results.filter { it.result == discover.discover.id }
                .getOrNull(0)
        verificationResult?.success ?: false
    }
}
