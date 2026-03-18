package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import moscowrealtime.composeapp.generated.resources.no_discovers
import moscowrealtime.composeapp.generated.resources.start_quest
import moscowrealtime.composeapp.generated.resources.start_with_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyHistoryForm(navigateQuests: () -> Unit) {
    Column(
        Modifier.Companion.fillMaxSize(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            10.dp,
            alignment = Alignment.Companion.CenterVertically
        )
    ) {
        Text(
            text = stringResource(Res.string.no_discovers),
            fontSize = 26.sp,
            color = titleTextColor,
            fontWeight = FontWeight.Companion.SemiBold,
            textAlign = TextAlign.Companion.Center
        )
        Text(
            text = stringResource(Res.string.start_with_quest),
            fontSize = 18.sp,
            color = secondaryTextColor,
            fontWeight = FontWeight.Companion.Normal,
            textAlign = TextAlign.Companion.Center
        )
        Spacer(Modifier.Companion)
        GradientButton(
            colors = listOf(blue.copy(0.8f), purple),
            modifier = Modifier.Companion.fillMaxWidth().padding(horizontal = 20.dp),
            onClick = navigateQuests,
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                stringResource(Res.string.start_quest),
                color = background
            )
        }
    }
}