package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.quests_library
import moscowrealtime.composeapp.generated.resources.view_all
import org.jetbrains.compose.resources.stringResource

@Composable
fun MiniQuestsLibraryHeader(modifier: Modifier = Modifier, onViewAllClick:() -> Unit) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        Text(
            text = stringResource(Res.string.quests_library),
            color = titleTextColor.copy(0.8f),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
        )
        Spacer(Modifier.weight(1f))
        Text(
            stringResource(Res.string.view_all),
            color = blue,
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.clickable(onClick = onViewAllClick)
        )
    }
}