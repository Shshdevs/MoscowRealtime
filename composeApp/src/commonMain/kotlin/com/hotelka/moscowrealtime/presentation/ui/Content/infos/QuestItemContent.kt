package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun QuestItemContent(quest: Quest) {
    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        KamelImage(
            { asyncPainterResource(quest.cover) },
            onLoading = {
                Box(Modifier.fillMaxSize().shimmerLoading(1000))
            },
            onFailure = {
                Box(Modifier.fillMaxSize().shimmerLoading(1000))
            },
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = "Quest Cover"
        )
        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier.align(Alignment.Bottom).padding(bottom = 2.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                quest.title,
                color = titleTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis

            )
            Text(
                quest.description,
                color = titleTextColor,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}