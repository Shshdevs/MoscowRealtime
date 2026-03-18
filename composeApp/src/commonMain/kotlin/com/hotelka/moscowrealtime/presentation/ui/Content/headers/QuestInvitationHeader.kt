package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun QuestInvitationHeader(modifier: Modifier = Modifier, quest: Quest) {
    Row(modifier) {
        KamelImage(
            { asyncPainterResource(quest.cover) },
            "Quest Cover",
            onLoading = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            onFailure = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(10.dp))
        Column(
            Modifier.heightIn(max = 150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                quest.title,
                color = titleTextColor.copy(0.8f),
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                quest.description,
                color = secondaryTextColor,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxHeight(),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}