package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun LocationItem(location: Location, subText: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        KamelImage(
            { asyncPainterResource(location.picture ?: "") },
            contentDescription = "Mini cover",
            onLoading = { Box(Modifier.fillMaxSize().shimmerLoading(500)) },
            onFailure = { Box(Modifier.fillMaxSize().shimmerLoading(500)) },
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .align(Alignment.Bottom)
                .weight(1f)
                .heightIn(min = 60.dp)
        ) {
            Text(
                location.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = titleTextColor.copy(alpha = 0.8f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(5.dp))

            Text(
                subText,
                fontSize = 12.sp,
                color = secondaryTextColor.copy(alpha = 0.8f)
            )
        }
    }
}