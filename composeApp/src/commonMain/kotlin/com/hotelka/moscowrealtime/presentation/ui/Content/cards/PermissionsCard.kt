package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.key
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PermissionsCard(permission: StringResource, onDismiss: () -> Unit) {
    DismissableCardFrame(
        modifier = Modifier.padding(top = 170.dp),
        onDismissRequest = onDismiss,
        content = {
            Spacer(Modifier.height(60.dp))
            Icon(
                painterResource(Res.drawable.key),
                "Permissions",
                tint = blue,
                modifier = Modifier.size(200.dp)
            )
            Spacer(Modifier.size(30.dp))
            Text(
                stringResource(permission),
                color = blue,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}