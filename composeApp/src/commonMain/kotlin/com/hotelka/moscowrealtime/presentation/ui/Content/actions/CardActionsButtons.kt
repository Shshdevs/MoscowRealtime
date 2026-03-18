package com.hotelka.moscowrealtime.presentation.ui.Content.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CardActionsButtons(
    mainActionText: StringResource,
    secondaryActionText: StringResource,
    belowActionText: StringResource,
    mainAction: () -> Unit,
    secondaryAction: () -> Unit,
    belowAction: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            OutlinedGradientButton(
                modifier = Modifier.weight(1f),
                shape = CircleShape,
                onClick = secondaryAction,
                content = {
                    Text(
                        stringResource(secondaryActionText),
                    )
                }
            )
            GradientButton(
                modifier = Modifier.weight(1f),
                shape = CircleShape,
                onClick = mainAction,
                content = {
                    Text(
                        stringResource(mainActionText),
                        color = background
                    )
                }
            )
        }
        GradientButton(
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            onClick = belowAction,
            content = {
                Text(
                    stringResource(belowActionText),
                    color = background
                )
            }
        )
    }
}