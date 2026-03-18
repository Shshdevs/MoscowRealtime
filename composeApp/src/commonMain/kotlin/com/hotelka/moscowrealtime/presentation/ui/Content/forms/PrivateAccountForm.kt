package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.lock
import moscowrealtime.composeapp.generated.resources.user_photos_private
import moscowrealtime.composeapp.generated.resources.user_profile_private
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrivateAccountForm(profilePrivate: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            10.dp,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(Res.drawable.lock), "Private",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(secondaryTextColor.copy(0.8f))
        )
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(if (profilePrivate) Res.string.user_profile_private else Res.string.user_photos_private),
            fontSize = 18.sp,
            color = secondaryTextColor,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}