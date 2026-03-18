package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.view_site
import org.jetbrains.compose.resources.stringResource

@Composable
fun ViewSiteButton(onClick: () -> Unit) {
    GradientButton(
        listOf(blue, purple),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(stringResource(Res.string.view_site), color = background)
    }
}