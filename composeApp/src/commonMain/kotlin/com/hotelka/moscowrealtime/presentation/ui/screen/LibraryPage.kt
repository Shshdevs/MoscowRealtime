package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.LibraryPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.LibraryViewModel

@Composable
fun LibraryPage(
    libraryViewModel: LibraryViewModel
) {
    val libraryPageUiModel by libraryViewModel.libraryPageUiModel.collectAsState()
    LibraryPageContent(
        libraryPageUiModel,
        libraryViewModel::onEvent
    )
}