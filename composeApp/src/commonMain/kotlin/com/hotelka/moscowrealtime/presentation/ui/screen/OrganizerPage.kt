package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.OrganizerPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.OrganizationGraphViewModel

@Composable
fun OrganizerPage(
    viewModel: OrganizationGraphViewModel
) {
    val orgGraphUiModel by viewModel.organizationGraphUiModel.collectAsState()
    orgGraphUiModel.navigatedOrganizerPageUiModel?.let { organizerPageUiModel ->
        OrganizerPageContent(
            organizerUiModel = organizerPageUiModel,
            onEvent = viewModel::onEvent
        )
    }
}