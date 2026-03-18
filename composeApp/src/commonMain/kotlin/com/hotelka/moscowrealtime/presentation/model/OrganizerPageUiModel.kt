package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.Organizer

data class OrganizerPageUiModel(
    val organizer: Organizer,
    val allowCRUD: Boolean,
    val createGroupDialogOpen: Boolean = false,
    val errorCreatingGroupDialogOpen: Boolean = false,
)