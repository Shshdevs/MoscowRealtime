package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.viewmodel.NetworkMonitorViewModel
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.no_connection
import moscowrealtime.composeapp.generated.resources.no_wifi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WifiCard(
    modifier: Modifier = Modifier,
    networkMonitor: NetworkMonitorViewModel = koinViewModel()
) {
    val isOnline by networkMonitor.isOnline.collectAsState()
    AnimatedVisibility(
        visible = !isOnline,
        modifier = modifier,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        ElevatedCard(
            modifier = Modifier.padding(top = 40.dp),
            colors = CardDefaults.elevatedCardColors(background),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Row(
                Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(Res.drawable.no_wifi),
                    "No connection",
                    tint = blue,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    stringResource(Res.string.no_connection),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = titleTextColor.copy(0.8f)
                )
            }
        }
    }
}