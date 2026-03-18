package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.QuestPreviewInfoHeader
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.edit
import moscowrealtime.composeapp.generated.resources.edit_info
import moscowrealtime.composeapp.generated.resources.go_right
import moscowrealtime.composeapp.generated.resources.quest_preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestPreviewInfo(tempQuest: TempQuest, onShowEditInfoAlert:() -> Unit) {
    Text(
        stringResource(Res.string.quest_preview),
        color = secondaryTextColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
    )
    DefaultAppCard(onClick = {}) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            QuestPreviewInfoHeader(tempQuest)
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = onShowEditInfoAlert,
                colors = ButtonDefaults.buttonColors(
                    contentColor = blue.copy(0.7f),
                    containerColor = blue.copy(0.1f)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    painterResource(Res.drawable.edit),
                    "Edit quest",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(5.dp))
                Text(stringResource(Res.string.edit_info), fontSize = 14.sp)
                Spacer(Modifier.weight(1f))
                Icon(
                    painterResource(Res.drawable.go_right),
                    "Go edit",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}