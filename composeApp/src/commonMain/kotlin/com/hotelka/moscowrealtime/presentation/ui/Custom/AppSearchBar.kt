package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.find_users
import moscowrealtime.composeapp.generated.resources.search
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppSearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onExpandedChange:(Boolean) -> Unit,
    searchAction: StringResource,
    focusManager: FocusManager
) {
    val searchBarState = rememberSearchBarState(SearchBarValue.Expanded)

    SearchBar(
        state = searchBarState,
        inputField = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(stringResource(searchAction))
                },
                label = {
                    Text(stringResource(Res.string.search))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Icon(painterResource(Res.drawable.search), "Search")
                },
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        onExpandedChange(focusState.isFocused)
                    },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = blue.copy(0.1f),
                    focusedContainerColor = background,
                    cursorColor = blue,
                    focusedTrailingIconColor = secondaryTextColor,
                    unfocusedTrailingIconColor = secondaryTextColor,
                    focusedLabelColor = secondaryTextColor,
                    unfocusedLabelColor = secondaryTextColor,
                    focusedTextColor = secondaryTextColor,
                    unfocusedTextColor = secondaryTextColor,
                    focusedIndicatorColor = blue,
                    unfocusedIndicatorColor = blue,
                    selectionColors = TextSelectionColors(
                        blue,
                        blue.copy(0.1f)
                    )
                )
            )
        },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = SearchBarDefaults.colors(
            containerColor = background,
        ),
    )
}