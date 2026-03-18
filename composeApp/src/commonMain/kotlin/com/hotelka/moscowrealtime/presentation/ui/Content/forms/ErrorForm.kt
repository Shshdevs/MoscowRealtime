package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.not_found
import moscowrealtime.composeapp.generated.resources.please_try_again
import moscowrealtime.composeapp.generated.resources.try_again
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorForm(modifier: Modifier, errorDescription: StringResource, onRetry: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(Res.drawable.not_found), "Error",
            modifier = Modifier.size(
                200.dp
            ),
            colorFilter = ColorFilter.tint(
                blue,
            )
        )
        Text(
            text = stringResource(errorDescription),
            fontSize = 26.sp,
            color = titleTextColor,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(Res.string.please_try_again),
            fontSize = 18.sp,
            color = secondaryTextColor,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier)
        GradientButton(
            colors = listOf(blue.copy(0.8f), purple),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            onClick = onRetry,
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                stringResource(Res.string.try_again),
                color = background
            )
        }
    }
}
