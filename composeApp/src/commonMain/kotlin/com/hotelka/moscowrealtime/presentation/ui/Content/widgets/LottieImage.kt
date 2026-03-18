package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import moscowrealtime.composeapp.generated.resources.Res

@Composable
fun LottieImage(modifier: Modifier, imageSize: Dp, jsonFileName: String) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/$jsonFileName.json").decodeToString()
            )
        }
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = Compottie.IterateForever,
        )

        Image(
            painter = rememberLottiePainter(
                composition,
                { progress }
            ),
            modifier = Modifier.size(imageSize),
            contentDescription = "Lottie animation"
        )
    }
}