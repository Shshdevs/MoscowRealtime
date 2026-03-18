package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.indigo

enum class PointType {
    REACHED, CURRENT, UNREACHED
}

@Composable
fun UnReachedPoint(modifier: Modifier) {
    Badge(modifier.size(60.dp), containerColor = indigo.copy(0.8f))
}

@Composable
fun ReachedPoint(modifier: Modifier) {
    Badge(modifier.size(60.dp), containerColor = blue.copy(0.9f))
}

@Composable
fun CurrentPoint(modifier: Modifier) {
    Badge(
        modifier
            .size(80.dp)
            .border(5.dp, blue, CircleShape)
            .padding(10.dp),
        containerColor = blue
    )
}
@Composable
fun RoutePoints(modifier: Modifier) {
    Badge(modifier.size(20.dp), containerColor = indigo.copy(0.9f))
}
