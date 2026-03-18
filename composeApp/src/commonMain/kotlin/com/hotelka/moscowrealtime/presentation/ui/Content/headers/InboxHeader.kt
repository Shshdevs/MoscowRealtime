package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.check_inbox
import org.jetbrains.compose.resources.stringResource

@Composable
fun InboxHeader() {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.28f)
            .background(background, RoundedCornerShape(bottomStartPercent = 25))
    ) {
        Column(Modifier.padding(30.dp)) {
            Spacer(Modifier.height(10.dp))
            Spacer(Modifier.size(50.dp))
            Spacer(Modifier.height(30.dp))
            Text(
                stringResource(Res.string.check_inbox),
                fontSize = 32.sp,
                modifier = Modifier.padding(start = 10.dp).fillMaxWidth(),
                color = darkBlue,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
            )

        }
    }
}