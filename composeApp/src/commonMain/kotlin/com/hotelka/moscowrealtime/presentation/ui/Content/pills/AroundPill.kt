package com.hotelka.moscowrealtime.presentation.ui.Content.pills

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.what_around
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AroundPill(hazeState: HazeState, onShowAround:() -> Unit) {
    Row(
        Modifier
            .wrapContentHeight()
            .clip(CircleShape)
            .border(
                BorderStroke(2.dp, background.copy(0.5f)),
                CircleShape
            )
            .hazeEffect(
                hazeState,
            ) {
                blurRadius = 20.dp
                tints = listOf(HazeTint(background.copy(0.4f)))
            }
            .clickable(onClick = onShowAround)
            .padding(
                vertical = 10.dp, horizontal = 15.dp
            )
    ) {
        Icon(
            painterResource(Res.drawable.location),
            "around",
            tint = background,
            modifier = Modifier.scale(0.7f)
        )
        Text(
            stringResource(Res.string.what_around),
            modifier = Modifier.align(Alignment.CenterVertically),
            color = background, fontSize = 14.sp
        )
    }
}