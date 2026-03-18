package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.OrganizationPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.OrganizationGraphViewModel

@Composable
fun OrganizationHomePage(
    viewModel: OrganizationGraphViewModel,
) {
    val orgGraphState by viewModel.organizationGraphUiModel.collectAsState()
    OrganizationPageContent(
        orgGraphUiModel = orgGraphState,
        viewModel::onEvent
    )
}