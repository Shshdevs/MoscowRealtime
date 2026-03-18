package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_quest
import moscowrealtime.composeapp.generated.resources.invitation_from
import moscowrealtime.composeapp.generated.resources.loading_user_error
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestInvitationCardHeader(quest: Quest?, user: User?) {
    Column {
        Row {
            KamelImage(
                { asyncPainterResource(quest?.cover ?: "") },
                "QuestCover",
                onLoading = {
                    Box(Modifier.fillMaxSize().shimmerLoading(500))
                },
                onFailure = {
                    Box(Modifier.fillMaxSize().shimmerLoading(500))
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.width(5.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    quest?.title ?: stringResource(Res.string.error_loading_quest),
                    color = titleTextColor.copy(0.8f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    quest?.description ?: stringResource(Res.string.error_loading_quest),
                    color = secondaryTextColor,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(Modifier.width(5.dp))

        Text(
            user?.name?.let { stringResource(Res.string.invitation_from) + it } ?: stringResource(
                Res.string.loading_user_error
            ),
            color = blue.copy(0.7f),
            fontSize = 14.sp,
        )

    }
}