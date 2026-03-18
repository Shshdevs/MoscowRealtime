package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.GroupParticipant
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.ExpandableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.GroupParticipantCardQuestProgress
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource

@Composable
fun GroupParticipantItem(
    user: GroupParticipant,
    quest: Quest?,
    editGroup: Boolean,
    onDelete: () -> Unit
) {
    ExpandableCardFrame(
        modifier = Modifier,
        showHorizDiver = false,
        topContent = {
            Row(Modifier) {
                UserItem(
                    Modifier.weight(1f),
                    user = user.user,
                    onClick = null
                )
                if (editGroup) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            painterResource(Res.drawable.trash),
                            "Go back",
                            tint = red
                        )
                    }
                }
            }
        },
        bottomContent = {
            quest?.let {
                GroupParticipantCardQuestProgress(
                    questProgress = user.questProgressValue,
                    quest = quest,
                    Modifier
                )
            }

        }
    )
}