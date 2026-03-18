package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.TempUserInfo
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.SettingsButton
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.ScorePill
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.UserProfilePicture
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor

@Composable
fun CurrUserProfileHeader(
    user: User,
    tempUserInfo: TempUserInfo?,
    userScore: List<Score>,
    onOpenSettings: () -> Unit,
    onScorePillClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Box(
            Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    listOf(
                        blue,
                        purple
                    )
                )
            )
                .padding(top = 200.dp)
                .background(background)
        )
        UserProfilePicture(Modifier.align(Alignment.BottomCenter), true,
            tempUserInfo?.tryPic ?: user.userPic
        )
        SettingsButton(Modifier.align(Alignment.TopEnd), onOpenSettings)
        ScorePill(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
                .padding(top = 20.dp),
            score = userScore.sumOf { it.score },
            onClick = onScorePillClick
        )
    }
    Text(
        tempUserInfo?.name ?: user.name,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = titleTextColor,
        textAlign = TextAlign.Center
    )
    Text(
        "@${tempUserInfo?.username?:user.username}",
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = secondaryTextColor,
        textAlign = TextAlign.Center
    )
}