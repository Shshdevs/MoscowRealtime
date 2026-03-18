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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeutralAlert(
    titleText: String,
    secondaryText: String,
    cancelText: String,
    submitText: String,
    onSubmit: () -> Unit,
    onDismissRequest: () -> Unit,
    middleContent: @Composable (ColumnScope) -> Unit ={},
    ){
    BasicAlertDialog(
        onDismissRequest,
        Modifier.Companion, DialogProperties(),
        {
            DialogCard(darkBlue) {
                Text(
                    titleText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Companion.SemiBold,
                    color = darkBlue.copy(1f),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    secondaryText,
                    fontSize = 14.sp,
                    color = darkBlue.copy(0.8f),
                    textAlign = TextAlign.Companion.Center
                )

                middleContent(this)
                Spacer(Modifier.Companion.height(5.dp))

                Button(
                    onClick = onSubmit, modifier = Modifier.Companion.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(darkBlue)
                ) {
                    Text(submitText, color = background, textAlign = TextAlign.Companion.Center)
                }
                OutlinedButton(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    onClick = onDismissRequest,
                    border = BorderStroke(2.dp, darkBlue)
                ) {
                    Text(
                        cancelText,
                        color = darkBlue,
                        textAlign = TextAlign.Companion.Center
                    )
                }
            }
        }
    )
}