package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.Location
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ReorderableQuestLocationItem(
    location: Location,
    position: Int,
    currentIndex: Int,
    itemsCount: Int,
    onDragStateChanged: (Boolean) -> Unit,
    onMove: (Int, Int) -> Unit,
    onRemove: () -> Unit,
    onDragFinished: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val itemSpacingPx = with(density) { 10.dp.toPx() }
    val dragDecisionThresholdPx = with(density) { 12.dp.toPx() }
    val removeThresholdXPx = with(density) { 96.dp.toPx() }

    var itemHeightPx by remember { mutableStateOf(1f) }

    val itemTranslationX = remember(location.id) { Animatable(0f) }
    val itemTranslationY = remember(location.id) { Animatable(0f) }

    var startIndex by remember(location.id) { mutableStateOf(currentIndex) }
    var draggedIndex by remember(location.id) { mutableStateOf(currentIndex) }
    var totalDragY by remember(location.id) { mutableStateOf(0f) }

    var accumulatedDx by remember(location.id) { mutableStateOf(0f) }
    var accumulatedDy by remember(location.id) { mutableStateOf(0f) }
    var dragMode by remember(location.id) { mutableStateOf(DragMode.Undecided) }

    val latestCurrentIndex by rememberUpdatedState(currentIndex)

    fun isThrownOutHorizontally(): Boolean {
        return kotlin.math.abs(itemTranslationX.value) > removeThresholdXPx
    }

    suspend fun resetPosition() {
        totalDragY = 0f
        accumulatedDx = 0f
        accumulatedDy = 0f
        dragMode = DragMode.Undecided

        coroutineScope {
            launch { itemTranslationX.animateTo(0f, spring()) }
            launch { itemTranslationY.animateTo(0f, spring()) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                itemHeightPx = it.height.toFloat().coerceAtLeast(1f)
            }
            .graphicsLayer {
                translationX = itemTranslationX.value
                translationY = itemTranslationY.value
            }
            .pointerInput(location.id, itemsCount, itemHeightPx, itemSpacingPx) {
                detectDragGesturesAfterLongPress(
                    onDragStart = {
                        startIndex = latestCurrentIndex
                        draggedIndex = latestCurrentIndex
                        totalDragY = 0f
                        accumulatedDx = 0f
                        accumulatedDy = 0f
                        dragMode = DragMode.Undecided
                        onDragStateChanged(true)
                    },
                    onDragEnd = {
                        onDragStateChanged(false)

                        scope.launch {
                            when (dragMode) {
                                DragMode.HorizontalRemove -> {
                                    val removedOut = isThrownOutHorizontally()
                                    Logger.withTag("RemoveDebug").d { removedOut.toString() }
                                    if (removedOut) {
                                        itemTranslationX.snapTo(0f)
                                        itemTranslationY.snapTo(0f)
                                        totalDragY = 0f
                                        accumulatedDx = 0f
                                        accumulatedDy = 0f
                                        dragMode = DragMode.Undecided
                                        onRemove()
                                    } else {
                                        resetPosition()
                                        onDragFinished()
                                    }
                                }

                                DragMode.VerticalReorder -> {
                                    resetPosition()
                                    onDragFinished()
                                }

                                DragMode.Undecided -> {
                                    resetPosition()
                                }
                            }
                        }
                    },
                    onDragCancel = {
                        onDragStateChanged(false)

                        scope.launch {
                            resetPosition()
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()

                        scope.launch {
                            accumulatedDx += dragAmount.x
                            accumulatedDy += dragAmount.y

                            if (dragMode == DragMode.Undecided) {
                                val absDx = kotlin.math.abs(accumulatedDx)
                                val absDy = kotlin.math.abs(accumulatedDy)

                                if (
                                    absDx > dragDecisionThresholdPx ||
                                    absDy > dragDecisionThresholdPx
                                ) {
                                    dragMode = if (absDx > absDy) {
                                        DragMode.HorizontalRemove
                                    } else {
                                        DragMode.VerticalReorder
                                    }
                                }
                            }

                            when (dragMode) {
                                DragMode.Undecided -> {}

                                DragMode.HorizontalRemove -> {
                                    itemTranslationX.snapTo(
                                        itemTranslationX.value + dragAmount.x
                                    )
                                }

                                DragMode.VerticalReorder -> {
                                    totalDragY += dragAmount.y

                                    itemTranslationY.snapTo(
                                        itemTranslationY.value + dragAmount.y
                                    )

                                    val itemStepPx = itemHeightPx + itemSpacingPx
                                    val indexOffset = (totalDragY / itemStepPx).roundToInt()

                                    val targetIndex =
                                        (startIndex + indexOffset).coerceIn(0, itemsCount - 1)

                                    if (targetIndex != draggedIndex) {
                                        onMove(draggedIndex, targetIndex)

                                        val movedBy = targetIndex - draggedIndex
                                        draggedIndex = targetIndex

                                        itemTranslationY.snapTo(
                                            itemTranslationY.value - movedBy * itemStepPx
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
    ) {
        QuestConstructorLocationItem(
            location = location,
            position = position,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

enum class DragMode { Undecided, HorizontalRemove, VerticalReorder }