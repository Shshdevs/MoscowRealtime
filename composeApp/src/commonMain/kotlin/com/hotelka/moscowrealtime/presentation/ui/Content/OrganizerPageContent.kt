package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.OrganizationGraphEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationNavEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizerPageEvents
import com.hotelka.moscowrealtime.presentation.model.OrganizerPageUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.CreateGroupAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ErrorCreatingGroupAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.OrganizerPageHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.BriefGroupItemCard
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NoGroupsItem
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.create_group
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrganizerPageContent(
    organizerUiModel: OrganizerPageUiModel,
    onEvent: (OrganizationGraphEvents) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(background)
        ) {
            OrganizerPageHeader(organizerUiModel.organizer.organizer, close = {
                onEvent(OrganizationNavEvents.OnNavigateBack)
            })
            LazyColumn(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                if (organizerUiModel.organizer.groups.isNotEmpty()) {
                    items(organizerUiModel.organizer.groups) { group ->
                        BriefGroupItemCard(
                            modifier = Modifier.fillMaxWidth(),
                            groupDetailed = group,
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
        if (organizerUiModel.allowCRUD) {
            GradientButton(
                modifier = Modifier.align(Alignment.BottomCenter).padding(10.dp)
                    .padding(bottom = 10.dp).fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = { onEvent(OrganizerPageEvents.OnChangeShowCreateGroupDialog)}
            ) {
                Text(stringResource(Res.string.create_group))
            }
        }
        if (organizerUiModel.createGroupDialogOpen) {
            CreateGroupAlert(
                onCreateGroup = {onEvent(OrganizerPageEvents.OnAddGroup(it))},
                groupExists = { name -> organizerUiModel.organizer.groups.map { it.group.groupName }.contains(name) },
                onDismiss = {onEvent(OrganizerPageEvents.OnChangeShowCreateGroupDialog) }
            )
        }

        if (organizerUiModel.errorCreatingGroupDialogOpen) {
            ErrorCreatingGroupAlert { onEvent(OrganizerPageEvents.OnShowCreateGroupDialogError) }
        }
    }
}