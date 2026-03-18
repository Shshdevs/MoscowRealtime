package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SwitchAction(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: StringResource,
    subLabel: StringResource,
    enabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                stringResource(label),
                color = titleTextColor,
                fontSize = 18.sp
            )
            Text(
                stringResource(subLabel),
                color = titleTextColor.copy(0.8f),
                fontSize = 12.sp
            )
        }
        Spacer(Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = blue,
                uncheckedTrackColor = secondaryTextColor.copy(0.2f),
                uncheckedThumbColor = secondaryTextColor,
                uncheckedBorderColor = secondaryTextColor,
                disabledCheckedTrackColor = secondaryTextColor.copy(0.2f),
                disabledCheckedThumbColor = secondaryTextColor,
                disabledCheckedBorderColor = secondaryTextColor
                ),
            enabled = enabled
        )
    }
}