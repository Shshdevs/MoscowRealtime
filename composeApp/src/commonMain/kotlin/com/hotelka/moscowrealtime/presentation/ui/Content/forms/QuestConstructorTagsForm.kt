package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.PillContainer
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add_circled
import moscowrealtime.composeapp.generated.resources.minus
import moscowrealtime.composeapp.generated.resources.quest_constructor_tag_min_length_error
import moscowrealtime.composeapp.generated.resources.your_custom_quest_tags
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestConstructorTagsForm(
    tags: List<String>,
    focusManager: FocusManager,
    addTag: (String) -> Unit,
    removeTag: (String) -> Unit
) {
    var tagValue by remember { mutableStateOf("") }
    CustomTextField(
        value = tagValue,
        onValueChange = { tagValue = it },
        isError = tagValue.length > 30,
        trailingIcon = {
            IconButton(onClick = {
                if (tagValue.isNotBlank() && tagValue.length <= 30) {
                    addTag(tagValue)
                    tagValue = ""
                }
            }) {
                Icon(
                    painterResource(Res.drawable.add_circled),
                    "Add tag"
                )
            }
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        label = { Text(stringResource(Res.string.your_custom_quest_tags)) },
        errorDescription = { Text(stringResource(Res.string.quest_constructor_tag_min_length_error)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        singleLine = true,
        color = blue
    )
    Spacer(Modifier.height(20.dp))
    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.sortedBy { it.length }.forEach { tag ->
            PillContainer(
                modifier = Modifier.wrapContentWidth(),
                color = blue,
                onClick = { removeTag(tag) }
            ) {
                Text(
                    tag.replaceFirstChar { it.uppercaseChar() },
                    color = background,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.width(5.dp))
                Icon(
                    painterResource(Res.drawable.minus),
                    "Remove tag",
                    tint = background
                )
            }
        }
    }
}
