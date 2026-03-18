package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.state.listState.LocationsListState
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NothingFoundItem
import com.hotelka.moscowrealtime.presentation.ui.Custom.AppSearchBar
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import kotlinx.coroutines.delay
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.find_users
import moscowrealtime.composeapp.generated.resources.found_with_query
import moscowrealtime.composeapp.generated.resources.recommended
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchLocationsForm(
    modifier: Modifier = Modifier,
    finalQuery: String,
    locationsListState: LocationsListState,
    locationSearchItem: @Composable ColumnScope.(Location) -> Unit,
    searchLocations: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) {
        delay(500)
        if (query != finalQuery || finalQuery.isEmpty()){
            searchLocations(query)
        }
    }

    Column(
        modifier
            .background(background)
    ) {
        AppSearchBar(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            query = query,
            onQueryChange = { query = it },
            onExpandedChange = { if (!it) focusManager.clearFocus() },
            searchAction = Res.string.find_users,
            focusManager = focusManager
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Spacer(Modifier)
            }
            item {
                Text(
                    if (finalQuery.isEmpty()) stringResource(Res.string.recommended) else stringResource(
                        Res.string.found_with_query
                    ) + finalQuery,
                    color = titleTextColor.copy(0.8f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            when (locationsListState) {
                is LocationsListState.Error -> {
                    item {
                        ErrorForm(
                            Modifier.fillMaxSize(),
                            Res.string.something_went_wrong
                        ) { searchLocations(query) }
                    }
                }

                LocationsListState.Loading -> {
                    item { LoadingForm(Modifier.fillMaxSize()) }
                }

                is LocationsListState.Success -> {
                    if (locationsListState.locations.isEmpty()) {
                        item { NothingFoundItem() }
                    } else {
                        items(locationsListState.locations, key = {it.id}) { user ->
                            locationSearchItem(user)
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(10.dp)) }
        }
    }
}