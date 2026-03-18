package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import moscowrealtime.composeapp.generated.resources.continue_discover
import moscowrealtime.composeapp.generated.resources.welcome_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInHeader(onGoBack:() -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.29f)
            .background(background, RoundedCornerShape(bottomStartPercent = 25))
    ) {
        Column(Modifier.padding(30.dp)) {
            Spacer(Modifier.height(10.dp))
            Icon(
                painterResource(Res.drawable.arrow_previous),
                tint = darkBlue,
                modifier = Modifier.size(50.dp).clip(CircleShape).padding(10.dp)
                    .clickable(onClick = onGoBack),
                contentDescription = "Back"
            )
            Spacer(Modifier.height(30.dp))
            Text(
                stringResource(Res.string.welcome_back),
                fontSize = 32.sp,
                modifier = Modifier.padding(start = 10.dp).fillMaxWidth(),
                color = darkBlue,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                textAlign = TextAlign.Justify
            )
            Text(
                stringResource(Res.string.continue_discover),
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),
                color = darkBlue,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Justify
            )
        }
    }
}