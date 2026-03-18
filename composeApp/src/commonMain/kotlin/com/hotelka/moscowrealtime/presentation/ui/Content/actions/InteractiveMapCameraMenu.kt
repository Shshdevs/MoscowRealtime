package com.hotelka.moscowrealtime.presentation.ui.Content.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.gallery
import moscowrealtime.composeapp.generated.resources.map_location_track
import moscowrealtime.composeapp.generated.resources.scan
import org.jetbrains.compose.resources.painterResource

@Composable
fun InteractiveMapCameraMenu(
    modifier: Modifier,
    onPickGallery:() -> Unit,
    onTakePic:() -> Unit,
    onShowMap:() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp)
            .background(White, CircleShape)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = onPickGallery) {
            Icon(
                painterResource(Res.drawable.gallery),
                tint = blue,
                contentDescription = "Open Gallery"
            )
        }

        FloatingActionButton(
            onClick = onTakePic,
            shape = CircleShape,
            modifier = Modifier
                .size(60.dp)
                .border(3.dp, blue, CircleShape)
                .padding(5.dp),
            containerColor = blue,
            contentColor = background,
            elevation = FloatingActionButtonDefaults.elevation(5.dp)
        ) {
            Icon(
                painterResource(Res.drawable.scan),
                "Scan",
            )
        }
        IconButton(
            onClick = onShowMap) {
            Icon(
                painterResource(Res.drawable.map_location_track),
                tint = blue,
                contentDescription = "Open Gallery"
            )
        }
    }
}