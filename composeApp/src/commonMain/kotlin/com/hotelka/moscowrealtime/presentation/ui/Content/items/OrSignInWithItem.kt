package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.or_sign_in_with
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrSignInWithItem() {
    Box(contentAlignment = Alignment.Center) {
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .padding(horizontal = 10.dp), 1.dp, background
        )
        Text(
            stringResource(Res.string.or_sign_in_with),
            modifier = Modifier.Companion
                .background(darkBlue)
                .padding(5.dp),
            color = background
        )
    }
}