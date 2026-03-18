package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ShimmeringButton(
    text: StringResource,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(15.dp),
    modifier: Modifier = Modifier,
) {
    GradientButton(
        colors = listOf(blue, purple),
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        additionalModifier = Modifier.Companion.shimmerLoading(500, background.copy(0.5f)),
        shape = shape
    ) {
        Text(
            stringResource(text),
            color = background
        )
    }
}