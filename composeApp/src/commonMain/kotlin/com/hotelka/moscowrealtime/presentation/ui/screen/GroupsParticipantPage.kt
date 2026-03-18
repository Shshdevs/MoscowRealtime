package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.GroupsParticipantPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.OrganizationGraphViewModel

@Composable
fun GroupsParticipantPage(
    viewModel: OrganizationGraphViewModel
) {
    val organizationGraphUiModel by viewModel.organizationGraphUiModel.collectAsState()
    GroupsParticipantPageContent(
        organizationGraphUiModel.currUserGroups,
        onEvent = viewModel::onEvent
    )
}
