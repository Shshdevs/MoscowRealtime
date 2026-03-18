package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.GroupPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.OrganizationGraphViewModel

@Composable
fun GroupPage(
    viewModel: OrganizationGraphViewModel,
) {
    val orgGraphUiModel by viewModel.organizationGraphUiModel.collectAsState()
    orgGraphUiModel.navigatedGroupPageUiModel?.let { groupPageUiModel ->
        GroupPageContent(
            groupPageUiModel,
            onEvent = viewModel::onEvent
        )
    }
}