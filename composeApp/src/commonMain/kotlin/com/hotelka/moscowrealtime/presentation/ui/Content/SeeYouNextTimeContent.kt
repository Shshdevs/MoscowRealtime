package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_to_auth_page
import moscowrealtime.composeapp.generated.resources.good_bye_quote
import moscowrealtime.composeapp.generated.resources.good_bye_title
import moscowrealtime.composeapp.generated.resources.sad_tear
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeeYouNextTimeContent(onContinueToAuthPage:() -> Unit) {
    Box(Modifier.fillMaxSize().background(background)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(Res.drawable.sad_tear),
                "Sad",
                tint = blue,
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(10.dp))

            Text(
                stringResource(Res.string.good_bye_title),
                color = titleTextColor,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                stringResource(Res.string.good_bye_quote),
                color = secondaryTextColor,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            Spacer(Modifier.height(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                onClick = onContinueToAuthPage,
                colors = ButtonDefaults.buttonColors(
                    blue
                )
            ) {
                Text(stringResource(Res.string.continue_to_auth_page))
            }
        }
    }
}