package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.api.ClosestLocationResult
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.distance
import moscowrealtime.composeapp.generated.resources.near_you
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NearYouWidget(closestLocation: ClosestLocationResult?, navigateMap:() -> Unit) {
    DefaultAppCard(
        cornerRadius = 30.dp,
        onClick = navigateMap
    ) {
        if (closestLocation != null) {
            Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                closestLocation.closestLocation?.picture?.let {
                    KamelImage(
                        { asyncPainterResource(it) },
                        modifier = Modifier.size(80.dp).clip(RoundedCornerShape(10.dp)),
                        onLoading = {
                            Box(Modifier.fillMaxSize().shimmerLoading(500))
                        },
                        contentScale = ContentScale.Crop,
                        contentDescription = "Closest Location Picture"
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column(
                    modifier = Modifier.align(Alignment.Bottom).padding(bottom = 2.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        stringResource(Res.string.near_you) + closestLocation.closestLocation?.label,
                        color = titleTextColor,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        stringResource(Res.string.distance) + closestLocation.distance + "km",
                        color = titleTextColor,
                        fontSize = 12.sp,
                    )
                }
            }
        } else {
            Box(Modifier.height(100.dp).fillMaxWidth().shimmerLoading(500))
        }
    }
}