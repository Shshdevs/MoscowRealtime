package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.resend_email_verification
import moscowrealtime.composeapp.generated.resources.send_email_verification
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailVerificationButton(timer: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = darkBlue,
            disabledContainerColor = background.copy(0.7f),
            disabledContentColor = darkBlue.copy(0.7f),
        ),
        enabled = timer > 30,
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
    ) {
        Text(
            if (timer <= 30) stringResource(Res.string.resend_email_verification) + " (${30 - timer})"
            else stringResource(Res.string.send_email_verification),
            textAlign = TextAlign.Center
        )
    }
}