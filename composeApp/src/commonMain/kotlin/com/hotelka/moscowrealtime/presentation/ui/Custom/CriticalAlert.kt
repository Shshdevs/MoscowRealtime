package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriticalAlert(
    titleText: String,
    secondaryText: String,
    cancelText: String,
    submitText: String,
    onSubmit: () -> Unit,
    onDismissRequest: () -> Unit,
    timerOn: Boolean = true,
    middleContent: @Composable ((ColumnScope) -> Unit)? = null,
) {
    var timer by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (timer < 10) {
            timer++
            delay(1000)
        }
    }
    BasicAlertDialog(
        onDismissRequest,
        Modifier.Companion, DialogProperties(),
        {
            DialogCard(red) {
                Text(
                    titleText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Companion.SemiBold,
                    color = red.copy(1f),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    secondaryText,
                    fontSize = 14.sp,
                    color = red.copy(0.8f),
                    textAlign = TextAlign.Companion.Center
                )

                middleContent?.invoke(this)
                Spacer(Modifier.Companion.height(5.dp))

                Button(
                    onClick = {
                        if (timerOn) {
                            if (timer == 10) onSubmit()
                        } else {
                            onSubmit()
                        }
                    }, modifier = Modifier.Companion.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(red)
                ) {
                    Text(
                        submitText + if (timer < 10 && timerOn) "(${10 - timer})" else "",
                        color = background,
                        textAlign = TextAlign.Companion.Center
                    )
                }
                OutlinedButton(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    onClick = onDismissRequest,
                    border = BorderStroke(2.dp, secondaryTextColor)
                ) {
                    Text(
                        cancelText,
                        color = secondaryTextColor,
                        textAlign = TextAlign.Companion.Center
                    )
                }
            }
        }
    )
}