package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.continue_journey
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(greeting: String){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = background
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = greeting,
                    fontSize = 26.sp,
                    color = titleTextColor,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(Res.string.continue_journey),
                    fontSize = 18.sp,
                    color = titleTextColor,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    )
}