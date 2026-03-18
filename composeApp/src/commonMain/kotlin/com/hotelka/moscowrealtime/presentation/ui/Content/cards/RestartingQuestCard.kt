package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.restarting_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun RestartingQuestCard(visible: Boolean) {
    SlideVerticallyCardAnim(
        modifier = Modifier.fillMaxSize(),
        visible = visible
    ) {
        DismissableCardFrame(
            modifier = Modifier.fillMaxSize(),
            dismissAvailable = false,
            onDismissRequest = {},
            content = {
                LoadingForm(
                    modifier = Modifier.fillMaxSize(),
                    loadingAction = stringResource(Res.string.restarting_quest)
                )
            }
        )
    }
}