package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingAlert(loadingAction: String) {
    BasicAlertDialog(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 250.dp),
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
    ) {
        LoadingForm(Modifier.fillMaxSize(), loadingAction)
    }
}