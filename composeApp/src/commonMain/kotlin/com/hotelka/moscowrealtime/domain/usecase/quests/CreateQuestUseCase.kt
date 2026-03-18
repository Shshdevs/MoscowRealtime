package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.core.config.AppConfig
import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository
import com.hotelka.moscowrealtime.domain.repository.UploadPhotoRepository
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray

class CreateQuestUseCase(
    private val authRepository: AuthRepository,
    private val questRepository: QuestRepository,
    private val uploadPhotoRepository: UploadPhotoRepository,
) {
    suspend operator fun invoke(tempQuest: TempQuest): Result<String> = runCatching {
        val currUserId = authRepository.getCurrentUserId() ?: return Result.failure(
            UserNotAuthenticatedException()
        )
        val questCover = tempQuest.cover?.encodeToByteArray()
            ?.let { uploadPhotoRepository.uploadPhoto("objects", it) }?.getOrNull()
        val newQuest = Quest(
            id = "",
            title = tempQuest.title,
            description = tempQuest.description,
            cover = questCover?.let { AppConfig.supabaseURL+ "/storage/v1/object/public/objects/" + it } ?: AppConfig.defaultPlaceHolder,
            locations = tempQuest.locations.map { it.id },
            participantIds = emptyList(),
            participantsAmount = 0,
            tags = tempQuest.tags,
            authorId = currUserId
        )
        questRepository.createQuest(newQuest).getOrThrow()
    }
}