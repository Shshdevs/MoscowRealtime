package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.LottieImage
import com.hotelka.moscowrealtime.presentation.ui.theme.blue

@Composable
fun LoadingForm(modifier: Modifier = Modifier, loadingAction: String = "Loading...") {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LottieImage(Modifier, 200.dp, "loading_anim")

            Text(
                loadingAction,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )
            Spacer(Modifier.height(50.dp))
        }
    }
}