package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.sign_in
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInButton(onClick:() -> Unit) {
    Button(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = background,
            containerColor = darkBlue
        )
    ) {
        Text(
            stringResource(Res.string.sign_in)
        )
    }
}