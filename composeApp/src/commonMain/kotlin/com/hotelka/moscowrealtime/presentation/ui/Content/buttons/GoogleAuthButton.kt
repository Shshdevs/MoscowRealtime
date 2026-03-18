package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.google
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoogleAuthButton(onClick:() -> Unit) {
    OutlinedButton(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, background),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = background,
            containerColor = Transparent
        )
    ) {
        Icon(
            painterResource(Res.drawable.google),
            modifier = Modifier.scale(0.8f),
            contentDescription = "Google"
        )
        Spacer(Modifier.width(5.dp))
        Text(
            "Google"
        )
    }
}