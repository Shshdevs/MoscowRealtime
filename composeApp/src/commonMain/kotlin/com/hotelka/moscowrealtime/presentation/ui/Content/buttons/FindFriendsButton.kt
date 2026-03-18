package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.find_friends
import moscowrealtime.composeapp.generated.resources.friend
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FindFriendsButton(navigateFindFriendsScreen: () -> Unit) {
    GradientButton(
        listOf(blue, purple),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = navigateFindFriendsScreen
    ) {
        Icon(
            painterResource(Res.drawable.friend),
            "Add to Friends",

            )
        Spacer(Modifier.width(10.dp))
        Text(
            stringResource(Res.string.find_friends),
        )
    }
}