package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background

@Composable
fun DefaultAppCard(modifier: Modifier = Modifier, cornerRadius: Dp = 20.dp, onClick:(() -> Unit)?, content: @Composable BoxScope.() -> Unit) {
    if (onClick == null){
        ElevatedCard(
            modifier,
            colors = CardDefaults.cardColors(
                containerColor = background
            ),
            elevation = CardDefaults.cardElevation(4.dp, 6.dp),
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Box { content() }
        }
    } else {
        ElevatedCard(
            onClick = onClick, modifier,
            colors = CardDefaults.cardColors(
                containerColor = background
            ),
            elevation = CardDefaults.cardElevation(4.dp, 6.dp),
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Box { content() }
        }
    }
}