package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cover
import moscowrealtime.composeapp.generated.resources.edit
import moscowrealtime.composeapp.generated.resources.form
import moscowrealtime.composeapp.generated.resources.quest_constructor_description_max_length_error
import moscowrealtime.composeapp.generated.resources.quest_constructor_description_min_length_error
import moscowrealtime.composeapp.generated.resources.quest_constructor_title_max_length_error
import moscowrealtime.composeapp.generated.resources.quest_constructor_title_min_length_error
import moscowrealtime.composeapp.generated.resources.quest_description
import moscowrealtime.composeapp.generated.resources.quest_title
import moscowrealtime.composeapp.generated.resources.set_up_quest_cover
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestConstructorInfoForm(
    tempQuest: TempQuest,
    onQuestTitleChange: (String) -> Unit,
    onQuestDescriptionChange: (String) -> Unit,
    addTag: (String) -> Unit,
    removeTag: (String) -> Unit,
    onUploadQuestCover: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    CustomTextField(
        value = tempQuest.title,
        onValueChange = onQuestTitleChange,
        isError = false,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.edit),
                "Quest Name "
            )
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        label = { Text(stringResource(Res.string.quest_title)) },
        errorDescription = {
            Text(
                if (tempQuest.title.isBlank()) stringResource(Res.string.quest_constructor_title_min_length_error)
                else if (tempQuest.title.length > 300) stringResource(Res.string.quest_constructor_title_max_length_error)
                else ""
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        singleLine = false,
        color = blue
    )
    CustomTextField(
        value = tempQuest.description,
        onValueChange = onQuestDescriptionChange,
        isError = false,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.form),
                "Quest description"
            )
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        label = {
            Text(stringResource(Res.string.quest_description))
        },
        errorDescription = {
            Text(
                if (tempQuest.description.length < 20) stringResource(Res.string.quest_constructor_description_min_length_error)
                else if (tempQuest.description.length > 700) stringResource(Res.string.quest_constructor_description_max_length_error)
                else ""
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        singleLine = false,
        color = blue
    )
    QuestConstructorTagsForm(
        tags = tempQuest.tags,
        focusManager = focusManager,
        addTag = addTag,
        removeTag = removeTag
    )
    Spacer(Modifier.height(10.dp))

    OutlinedButton(
        onClick = onUploadQuestCover,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(background, blue.copy(0.7f)),
        border = BorderStroke(2.dp, blue.copy(0.7f))
    ) {
        Icon(painterResource(Res.drawable.cover), "Set up cover")
        Spacer(Modifier.width(5.dp))
        Text(stringResource(Res.string.set_up_quest_cover))
    }
}
