package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuitMapsButton(
    modifier: Modifier = Modifier,
    backToText: StringResource,
    onClick:() -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(White, blue),
        modifier = modifier.padding(top = 40.dp, start = 20.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
    ) {
        Icon(painterResource(Res.drawable.arrow_previous), "Back")
        Spacer(Modifier.width(5.dp))
        Text(stringResource(backToText))
    }
}