package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun DiscoverFrame(
    onDismiss: () -> Unit,
    headerContent: @Composable BoxScope.(Dp) -> Unit,
    headerInfo:@Composable BoxScope.(Dp) -> Unit,
    mainContent: @Composable ColumnScope.(LazyListState) -> Unit
) {
    val bottomFade = Brush.verticalGradient(
        0.6f to White,
        0.9f to White.copy(0.2f),
        1f to Transparent
    )
    val maxAppBarHeight = 550.dp
    val minAppBarHeight = 86.dp
    val lazyListState = rememberLazyListState()
    val scrollOffset = remember { mutableFloatStateOf(0f) }

    var offsetY by remember { mutableFloatStateOf(50f) }
    val maxOffset = LocalWindowInfo.current.containerSize.height.toFloat() * 0.5f

    val animatedOffset by animateFloatAsState(
        targetValue = offsetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_offset"
    )


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = scrollOffset.floatValue + delta
                scrollOffset.floatValue = newOffset.coerceIn(-maxAppBarHeight.value, 0f)
                return Offset.Zero
            }
        }
    }


    val appBarHeight by animateDpAsState(
        targetValue = if (scrollOffset.floatValue == 0f) maxAppBarHeight else minAppBarHeight,
        animationSpec = tween(600)
    )
    val titleHeight by animateDpAsState(
        targetValue = if (scrollOffset.floatValue == 0f) maxAppBarHeight else 0.dp,
        animationSpec = tween(400)
    )

    Box(
        Modifier
            .fillMaxSize()
            .offset { IntOffset(0, animatedOffset.roundToInt()) }
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Transparent,
                        Black.copy(1f)
                    )
                )
            )
    ) {
        ElevatedCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.95f)
                .fillMaxWidth()
                .nestedScroll(nestedScrollConnection),

            shape = RoundedCornerShape(
                topStart = 50.dp, topEnd = 50.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = background
            )
        ) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    Modifier
                        .height(appBarHeight)
                        .pointerInput(Unit) {
                            detectVerticalDragGestures(
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume()
                                    if (offsetY + dragAmount > 50f) {
                                        offsetY += dragAmount
                                    }
                                },
                                onDragEnd = {
                                    if (abs(offsetY) > maxOffset * 0.3f) {
                                        onDismiss()
                                    } else {
                                        offsetY = 50f
                                    }
                                }
                            )
                        }
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                            .drawWithContent {
                                drawContent()
                                drawRect(brush = bottomFade, blendMode = BlendMode.DstIn)
                            }) {
                        headerContent(appBarHeight)
                    }
                    headerInfo(titleHeight)
                }
                mainContent(lazyListState)
            }
        }
    }
}