package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.FazingCover
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.be_first_this_time
import moscowrealtime.composeapp.generated.resources.continue_quest
import moscowrealtime.composeapp.generated.resources.reached
import org.jetbrains.compose.resources.stringResource

@Composable
fun ThePointIsReachedByUserCard(
    user: User,
    location: Location,
    onContinueQuest: () -> Unit,
) {
    DismissableCardFrame(
        modifier = Modifier.fillMaxSize().padding(top = 40.dp),
        dismissAvailable = false,
        onDismissRequest = {},
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FazingCover(
                    location.picture.toString(),
                    direction = -1,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = user.name + stringResource(Res.string.reached) + location.label + "!",
                    fontSize = 26.sp,
                    color = titleTextColor,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(Res.string.be_first_this_time),
                    fontSize = 18.sp,
                    color = secondaryTextColor,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                GradientButton(
                    colors = listOf(blue.copy(0.8f), purple),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = onContinueQuest,
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        stringResource(Res.string.continue_quest)
                    )
                }
            }
        }
    )
}