package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.delete_account
import moscowrealtime.composeapp.generated.resources.log_out
import moscowrealtime.composeapp.generated.resources.red_zone
import moscowrealtime.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsRedZone(
    onLogOut:() -> Unit,
    onDeleteAccount:() -> Unit,
) {
    Text(
        stringResource(Res.string.red_zone),
        color = red,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(5.dp))

    OutlinedButton(
        onClick = onLogOut,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(2.dp, red),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            stringResource(Res.string.log_out),
            color = red,
        )
    }

    Spacer(Modifier.height(5.dp))

    Button(
        onClick = onDeleteAccount,
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(red),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Icon(
            painterResource(Res.drawable.trash),
            "Delete account",
            tint = background
        )
        Spacer(Modifier.width(5.dp))
        Text(
            stringResource(Res.string.delete_account),
            color = background,
        )
    }
}