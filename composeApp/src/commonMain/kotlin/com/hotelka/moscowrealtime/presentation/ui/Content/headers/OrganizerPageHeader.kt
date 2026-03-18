package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.LargeUserPictureCover
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizerPageHeader(user: User, close:() -> Unit) {
    MediumTopAppBar(
        title = {
            Row(
                Modifier
                    .background(background)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                LargeUserPictureCover(user.userPic, false)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.height(90.dp), verticalArrangement = Arrangement.Bottom) {
                    Text(
                        user.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = titleTextColor.copy(0.8f)
                    )
                    Text(
                        "@${user.username}",
                        fontSize = 20.sp,
                        color = secondaryTextColor
                    )
                }
            }
        },
        modifier = Modifier.shadow(5.dp),
        navigationIcon = {
            IconButton(
                onClick = close
            ) {
                Icon(
                    painterResource(Res.drawable.arrow_previous),
                    "Go back",
                    tint = titleTextColor.copy(0.8f)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(background)
    )

}