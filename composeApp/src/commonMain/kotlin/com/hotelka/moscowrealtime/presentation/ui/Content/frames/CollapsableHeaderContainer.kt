package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CollapsableHeaderContainer(
    modifier: Modifier = Modifier,
    maxHeaderHeight: Dp = 350.dp,
    minHeaderHeight: Dp = 100.dp,
    headerContent: @Composable (BoxScope.(Dp) -> Unit),
    content: @Composable (ColumnScope.() -> Unit)
) {
    val scope = rememberCoroutineScope()
    val collapseRange = maxHeaderHeight - minHeaderHeight
    val toolbarOffset = remember { Animatable(0f) }

    val currentHeaderHeight = (maxHeaderHeight + toolbarOffset.value.dp).coerceAtLeast(minHeaderHeight)

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val progress = toolbarOffset.value / -collapseRange.value

                val resistance = if (available.y > 0) {
                    0.3f + (1f - progress.coerceIn(0f, 1f)) * 0.3f
                } else {
                    0.8f + (1f - progress.coerceIn(0f, 1f)) * 0.4f
                }.coerceIn(0.2f, 1.2f)

                val delta = available.y * resistance
                val newOffset =
                    (toolbarOffset.value + delta)
                        .coerceIn(-collapseRange.value, 0f)

                val consumed = newOffset - toolbarOffset.value
                scope.launch {
                    toolbarOffset.snapTo(newOffset)
                }
                return Offset(0f, consumed)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (toolbarOffset.value > -5f && toolbarOffset.value < 5f) {
                    toolbarOffset.animateTo(0f)
                } else if (kotlin.math.abs(toolbarOffset.value + collapseRange.value) < 5f) {
                    toolbarOffset.animateTo(-collapseRange.value)
                } else {
                    val flingVelocity = available.y
                    if (kotlin.math.abs(flingVelocity) > 100f) {
                        val targetOffset = if (flingVelocity < 0) -collapseRange.value else 0f
                        toolbarOffset.animateTo(
                            targetValue = targetOffset,
                            animationSpec = spring(
                                dampingRatio = 0.7f,
                                stiffness = 100f
                            )
                        )
                    }
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    Column(
        modifier.fillMaxSize().nestedScroll(nestedScrollConnection),
    ) {
        Box(Modifier.fillMaxWidth().height(currentHeaderHeight)) {
            headerContent(currentHeaderHeight)
        }
        content()
    }
}