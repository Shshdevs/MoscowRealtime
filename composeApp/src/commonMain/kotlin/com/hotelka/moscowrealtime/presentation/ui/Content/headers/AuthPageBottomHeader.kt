package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.AuthGraphEvents
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.CreateAccountButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.SignInButton
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.immerse_yourself
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthPageBottomHeader(
    modifier: Modifier,
    onEvent: (AuthGraphEvents) -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .background(background, RoundedCornerShape(topEndPercent = 25))
    ) {
        Column(
            Modifier
                .padding(20.dp)
                .padding(end = 40.dp, top = 10.dp, start = 10.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                stringResource(Res.string.immerse_yourself),
                fontSize = 18.sp,
                color = darkBlue,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 30.sp,
            )

            SignInButton { onEvent(AuthGraphEvents.NavigateSignIn) }

            CreateAccountButton(Modifier.align(Alignment.End)) { onEvent(AuthGraphEvents.NavigateSignUp) }
        }
    }
}