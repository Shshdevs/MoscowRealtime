package com.hotelka.moscowrealtime.presentation.ui.Content.frames

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.theme.blue

@Composable
fun ExpandableCardFrame(
    modifier: Modifier,
    showHorizDiver: Boolean = true,
    topContent: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable ColumnScope.() -> Unit

) {
    var isExpanded by remember { mutableStateOf(false) }
    DefaultAppCard(
        modifier = modifier,
        onClick = {
            isExpanded = !isExpanded
            Logger.withTag("QuestProgress").d { isExpanded.toString() }
        },
    ) {
        Column(Modifier.padding(10.dp).animateContentSize()) {
            topContent()
            Spacer(Modifier.Companion.height(5.dp))
            if (isExpanded) {
                if (showHorizDiver) HorizontalDivider(
                    Modifier.Companion.padding(horizontal = 5.dp).padding(start = 80.dp)
                        .clip(CircleShape),
                    2.dp,
                    blue
                )
                Spacer(Modifier.Companion.height(10.dp))
                bottomContent()
            }
        }
    }
}