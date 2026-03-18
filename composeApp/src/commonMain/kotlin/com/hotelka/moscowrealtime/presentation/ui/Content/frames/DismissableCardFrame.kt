package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun DismissableCardFrame(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    dismissAvailable: Boolean = true,
    onDismissRequest:() -> Unit,
    content:@Composable ColumnScope.() -> Unit,
) {

    var offsetY by remember { mutableFloatStateOf(100f) }
    val maxOffset = LocalWindowInfo.current.containerSize.height.toFloat() * 0.5f

    val animatedOffset by animateFloatAsState(
        targetValue = offsetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_offset"
    )

    ElevatedCard(
        modifier
            .offset { IntOffset(0, animatedOffset.roundToInt()) },
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = background
        ),
        elevation = CardDefaults.elevatedCardElevation(5.dp)
    ) {
        Column(
            Modifier.Companion
                .fillMaxSize()
                .background(background)
                .padding(10.dp)
                .padding(top = 20.dp)
                .pointerInput(Unit) {
                    if (dismissAvailable) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, dragAmount ->
                                change.consume()
                                if (offsetY + dragAmount > 100f) {
                                    offsetY += dragAmount
                                }
                            },
                            onDragEnd = {
                                if (abs(offsetY) > maxOffset * 0.3f) {
                                    onDismissRequest()
                                } else {
                                    offsetY = 100f
                                }
                            }
                        )
                    }
                }
                .padding(paddingValues),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
        ) {
            content()
        }
    }
}