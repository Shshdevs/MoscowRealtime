package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.find_friends
import moscowrealtime.composeapp.generated.resources.find_new_friends
import moscowrealtime.composeapp.generated.resources.friend
import moscowrealtime.composeapp.generated.resources.no_friends
import moscowrealtime.composeapp.generated.resources.oh
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NoFriendsForm(navigateFindFriendsScreen:() -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.friend),
            "No Friends",
            modifier = Modifier.size(200.dp),
            tint = blue
        )
        Spacer(Modifier)
        Text(
            text = stringResource(Res.string.oh) + " " + stringResource(Res.string.no_friends),
            fontSize = 26.sp,
            color = titleTextColor.copy(0.8f),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(Res.string.find_new_friends),
            fontSize = 18.sp,
            color = secondaryTextColor,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        GradientButton(
            listOf(blue, purple),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            onClick = navigateFindFriendsScreen
        ) {
            Icon(
                painterResource(Res.drawable.friend),
                "Add to Friends"
            )
            Spacer(Modifier.width(10.dp))
            Text(
                stringResource(Res.string.find_friends),
                color = background
            )
        }
    }
}