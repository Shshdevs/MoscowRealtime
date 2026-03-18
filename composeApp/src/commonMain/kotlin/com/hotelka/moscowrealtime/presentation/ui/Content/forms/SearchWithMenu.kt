package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.Custom.AppSearchBar
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.find_users
import moscowrealtime.composeapp.generated.resources.search_quests

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchWithMenu(filterContent:(String) -> Unit, menuContents: @Composable ColumnScope.() -> Unit) {
    val focusManager = LocalFocusManager.current
    var isSearchFocused by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isSearchFocused,
        onExpandedChange = {
            focusManager.clearFocus()
        }
    ) {
        AppSearchBar(
            Modifier.fillMaxWidth().menuAnchor(
                ExposedDropdownMenuAnchorType.PrimaryEditable,
                true
            ),
            query = query,
            onQueryChange = { query = it; filterContent(it) },
            onExpandedChange = { isSearchFocused = it },
            searchAction = Res.string.search_quests,
            focusManager = focusManager,
        )

        ExposedDropdownMenu(
            expanded = isSearchFocused,
            onDismissRequest = {
                focusManager.clearFocus()
            },
            containerColor = background,
            shape = RoundedCornerShape(20.dp),
        ) {
            menuContents()
        }
    }
}