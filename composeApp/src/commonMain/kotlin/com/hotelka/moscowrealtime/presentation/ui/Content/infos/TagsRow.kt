package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.HazePillContainer
import dev.chrisbanes.haze.HazeState
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.hide
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TagsRow(
    modifier: Modifier,
    tags: List<String>,
    hazeState: HazeState,
    expandEnabled: Boolean = false
) {
    var isExpanded by remember { mutableStateOf(false) }

    AnimatedContent(
        targetState = isExpanded,
        transitionSpec = {
            fadeIn() with fadeOut() using SizeTransform(clip = false)
        },
        modifier = modifier
    ) { expanded ->
        if (expanded && expandEnabled) {
            ExpandedTagsContent(
                tags = tags,
                hazeState = hazeState,
                onHideClick = { isExpanded = false }
            )
        } else {
            CollapsedTagsContent(
                tags = tags,
                hazeState = hazeState,
                onExpandClick = { if (expandEnabled) isExpanded = true }
            )
        }
    }
}

@Composable
fun CollapsedTagsContent(
    tags: List<String>,
    hazeState: HazeState,
    onExpandClick: () -> Unit
) {
    SubcomposeLayout(Modifier) { constraints ->
        val containerWidth = constraints.maxWidth
        val placeables = mutableListOf<Placeable>()
        val spacing = 8.dp.roundToPx()
        var currentWidth = 0
        var localVisibleCount = 0
        var localOverflowCount = 0

        fun measureTag(tag: String): Placeable {
            return subcompose("tag_$tag") {
                HazePillContainer(hazeState) {
                    Text(
                        tag.replaceFirstChar { it.uppercaseChar() },
                        color = background.copy(0.7f),
                        fontSize = 14.sp
                    )
                }
            }.first().measure(constraints.copy(maxWidth = Constraints.Infinity))
        }

        fun measureOverflow(count: Int): Placeable {
            return subcompose("overflow") {
                HazePillContainer(
                    hazeState,
                    modifier = Modifier.clip(CircleShape).clickable { onExpandClick() }
                ) {
                    Text("+$count", color = background.copy(0.7f), fontSize = 14.sp)
                }
            }.first().measure(constraints.copy(maxWidth = Constraints.Infinity))
        }

        val measuredTags = tags.map { tag -> tag to measureTag(tag) }

        for ((_, placeable) in measuredTags) {
            val requiredWidth = if (placeables.isEmpty()) placeable.width
            else placeable.width + spacing

            if (currentWidth + requiredWidth <= containerWidth) {
                placeables.add(placeable)
                localVisibleCount++
                currentWidth += requiredWidth
            } else {
                localOverflowCount = tags.size - localVisibleCount
                break
            }
        }

        if (localOverflowCount > 0) {
            val overflowPlaceable = measureOverflow(localOverflowCount)
            val overflowRequiredWidth = if (placeables.isEmpty()) overflowPlaceable.width
            else overflowPlaceable.width + spacing

            while (placeables.isNotEmpty() &&
                currentWidth + overflowRequiredWidth > containerWidth
            ) {
                val removed = placeables.removeAt(placeables.lastIndex)
                currentWidth -= if (placeables.isEmpty()) removed.width
                else removed.width + spacing
                localVisibleCount--
                localOverflowCount++
            }

            if (currentWidth + overflowRequiredWidth <= containerWidth) {
                placeables.add(overflowPlaceable)
            }
        }

        layout(containerWidth, placeables.maxOfOrNull { it.height } ?: 0) {
            var x = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x, 0)
                x += placeable.width + spacing
            }
        }
    }
}

@Composable
fun ExpandedTagsContent(
    tags: List<String>,
    hazeState: HazeState,
    onHideClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEach { tag ->
                HazePillContainer(hazeState) {
                    Text(
                        tag.replaceFirstChar { it.uppercaseChar() },
                        color = background.copy(0.7f),
                        fontSize = 14.sp
                    )
                }
            }

            HazePillContainer(
                hazeState,
                modifier = Modifier.clip(CircleShape).clickable { onHideClick() }
            ) {
                Text(
                    stringResource(Res.string.hide),
                    color = background.copy(0.7f),
                    fontSize = 14.sp
                )
            }
        }
    }
}