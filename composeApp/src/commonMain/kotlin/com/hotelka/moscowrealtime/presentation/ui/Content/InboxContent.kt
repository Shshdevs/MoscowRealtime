package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.LottieImage
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.EmailVerificationButton
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.InboxHeader

@Composable
fun InboxContent(
    timer: Int,
    onSendEmailVerification:() -> Unit
) {
    Box(Modifier.fillMaxSize().background(darkBlue)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            InboxHeader()
            Spacer(Modifier.height(30.dp))

            LottieImage(
                Modifier.fillMaxWidth(),
                imageSize = 320.dp,
                jsonFileName = "inbox",
            )

            EmailVerificationButton(timer, onSendEmailVerification)
        }
    }
}