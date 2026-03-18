package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.presentation.events.QuestConstructorPageEvents
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.QuestConstructorInfoForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add_location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuestInfoAlert(
    contentVisible: Boolean,
    tempQuest: TempQuest,
    onEvent: (QuestConstructorPageEvents) -> Unit,
) {
    BasicAlertDialog(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp),
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
        content = {
            SlideVerticallyCardAnim(visible = contentVisible) {
                DismissableCardFrame(
                    onDismissRequest = { onEvent(QuestConstructorPageEvents.OnChangeShowEditInfo) }
                ) {
                    DismissableCardHeader(Res.string.add_location)
                    Spacer(Modifier.height(20.dp))
                    QuestConstructorInfoForm(
                        tempQuest = tempQuest,
                        onQuestTitleChange = { onEvent(QuestConstructorPageEvents.OnUpdateQuestTitle(it)) },
                        onQuestDescriptionChange = { onEvent(QuestConstructorPageEvents.OnUpdateQuestDescription(it))},
                        onUploadQuestCover = { onEvent(QuestConstructorPageEvents.OnSetQuestCover) },
                        addTag = { onEvent(QuestConstructorPageEvents.OnAddTag(it)) },
                        removeTag = { onEvent(QuestConstructorPageEvents.OnRemoveTag(it)) }
                    )
                }
            }
        }
    )
}
