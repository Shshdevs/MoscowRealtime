package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.detailed.QuestInvitationDetailed

sealed class QuestInvitationsUiState {
    object None: QuestInvitationsUiState()
    object Loading: QuestInvitationsUiState()
    data class Success(val invitations: List<QuestInvitationDetailed>): QuestInvitationsUiState()
    data class Error(val error: String): QuestInvitationsUiState()
}