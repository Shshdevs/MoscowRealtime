package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.PillContainer
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.default_quest_cover
import moscowrealtime.composeapp.generated.resources.your_custom_quest_description
import moscowrealtime.composeapp.generated.resources.your_custom_quest_title
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestPreviewInfoHeader(tempQuest: TempQuest, modifier: Modifier = Modifier) {
    Row(modifier, verticalAlignment = Alignment.Bottom) {

        Image(
            bitmap = tempQuest.cover ?: imageResource(Res.drawable.default_quest_cover),
            "Temp quest cover",
            modifier = Modifier
                .size(95.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(10.dp))
        Column(
            Modifier.heightIn(max = 95.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                tempQuest.title.ifBlank { stringResource(Res.string.your_custom_quest_title) },
                color = titleTextColor.copy(0.8f),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                tempQuest.description.ifBlank { stringResource(Res.string.your_custom_quest_description) },
                color = secondaryTextColor,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxLines = 1
            ) {
                tempQuest.tags.sortedBy { it.length }.forEach { text ->
                    PillContainer(
                        Modifier.height(40.dp),
                        blue,
                        paddingValues = PaddingValues(vertical = 5.dp, horizontal = 10.dp),
                        onClick = {}) {
                        Text(text, color = background, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}