package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.nothing_found
import moscowrealtime.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NothingFoundItem() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Image(
            painterResource(Res.drawable.search), "Failure",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp)),
            colorFilter = ColorFilter.tint(blue.copy(0.8f))
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = stringResource(Res.string.nothing_found),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = blue.copy(0.8f),
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )
    }
}