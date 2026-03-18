package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.OrganizationGraphEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationNavEvents
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.items.GroupItemCard
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NoGroupsItem
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import moscowrealtime.composeapp.generated.resources.my_groups
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsParticipantPageContent(
    groups: List<GroupDetailed>,
    onEvent:(OrganizationGraphEvents) -> Unit
) {
    Column(Modifier.fillMaxSize().background(background)) {
        MediumTopAppBar(
            modifier = Modifier.shadow(5.dp),
            title = {
                Text(
                    stringResource(Res.string.my_groups),
                    fontWeight = FontWeight.SemiBold,
                    color = titleTextColor.copy(0.8f)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {onEvent(OrganizationNavEvents.OnNavigateBack)}
                ) {
                    Icon(
                        painterResource(Res.drawable.arrow_previous),
                        "Go back",
                        tint = titleTextColor.copy(0.8f)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(background)
        )
        LazyColumn {
            if (groups.isNotEmpty()) {
                items(groups) { group ->
                    GroupItemCard(
                        Modifier.fillMaxWidth().padding(10.dp),
                        group,
                        navigateGroup = {onEvent(OrganizationNavEvents.OnNavigateGroup(group))}
                    )
                }
            } else {
                item {
                    Spacer(Modifier.height(10.dp))
                    NoGroupsItem()
                }
            }
        }
    }
}