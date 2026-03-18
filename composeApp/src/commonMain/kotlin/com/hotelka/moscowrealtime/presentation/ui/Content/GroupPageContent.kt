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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.GroupEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationGraphEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationNavEvents
import com.hotelka.moscowrealtime.presentation.model.GroupPageUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.GroupPageActionsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.DeleteGroupAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.DeleteGroupParticipantAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.AddParticipantsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.PinQuestCard
import com.hotelka.moscowrealtime.presentation.ui.Content.items.GroupParticipantItem
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NoGroupParticipantsItem
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NoQuestPinnedItem
import com.hotelka.moscowrealtime.presentation.ui.Content.items.QuestItemCard
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import moscowrealtime.composeapp.generated.resources.check
import moscowrealtime.composeapp.generated.resources.current_quest
import moscowrealtime.composeapp.generated.resources.edit
import moscowrealtime.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupPageContent(
    groupUiModel: GroupPageUiModel,
    onEvent: (OrganizationGraphEvents) -> Unit
) {
    Box(Modifier.fillMaxSize().background(background)) {
        Column(Modifier.fillMaxSize()) {
            MediumTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(OrganizationNavEvents.OnNavigateBack) }
                    ) {
                        Icon(
                            painterResource(Res.drawable.arrow_previous),
                            "Go back",
                            tint = titleTextColor.copy(0.8f)
                        )
                    }
                },
                actions = {
                    if (groupUiModel.allowCRUD) {
                        if (groupUiModel.editGroupModeOn) {
                            IconButton(
                                onClick = { onEvent(GroupEvents.OnChangeShowDeleteGroupDialog) }
                            ) {
                                Icon(
                                    painterResource(Res.drawable.trash),
                                    "",
                                    tint = red
                                )
                            }
                        }
                        IconButton(
                            onClick = { onEvent(GroupEvents.OnChangeEditGroup) }
                        ) {
                            Icon(
                                if (groupUiModel.editGroupModeOn) painterResource(Res.drawable.check)
                                else painterResource(Res.drawable.edit),
                                "Edit group",
                                tint = titleTextColor.copy(0.8f)
                            )
                        }
                    }
                },
                modifier = Modifier.shadow(5.dp),
                title = {
                    Text(
                        groupUiModel.group.group.groupName,
                        fontWeight = FontWeight.SemiBold,
                        color = titleTextColor.copy(0.8f)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(background)
            )
            LazyColumn(
                Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                if (groupUiModel.group.quest != null) {
                    item {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            stringResource(Res.string.current_quest),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = secondaryTextColor,
                            modifier = Modifier.padding(5.dp)
                        )
                        QuestItemCard(
                            groupUiModel.group.quest
                        ) { onEvent(OrganizationNavEvents.OnNavigateQuestPage(groupUiModel.group.quest.id)) }
                    }
                } else {
                    item {
                        Spacer(Modifier.height(10.dp))
                        NoQuestPinnedItem()
                    }
                }
                if (groupUiModel.group.users.isNotEmpty()) {
                    items(groupUiModel.group.users) { user ->
                        GroupParticipantItem(
                            user,
                            quest = groupUiModel.group.quest,
                            editGroup = groupUiModel.editGroupModeOn,
                            onDelete = { onEvent(GroupEvents.OnRemoveUserFromGroupAlert(user.user)) })
                    }
                } else {
                    item { NoGroupParticipantsItem() }
                }
            }
        }
        if (groupUiModel.allowCRUD) {
            GroupPageActionsCard(
                Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                onInviteParticipant = { onEvent(GroupEvents.OnChangeShowAddUserMenu) },
                onPinQuest = { onEvent(GroupEvents.OnChangeOpenPinQuestMenu) }
            )

            PinQuestCard(
                visible = groupUiModel.pinQuestDialogOpen,
                finalQuery = groupUiModel.searchQuestsQuery,
                questsSearchStateList = groupUiModel.searchedQuests,
                onSearchQuests = { onEvent(GroupEvents.OnSearchQuests(it)) },
                pinQuest = { quest -> onEvent(GroupEvents.OnPinQuest(quest.id)); onEvent(GroupEvents.OnChangeOpenPinQuestMenu) },
                navigateQuestPage = { quest -> onEvent(OrganizationNavEvents.OnNavigateQuestPage(quest.id)) },
                onDismiss = { onEvent(GroupEvents.OnChangeOpenPinQuestMenu) }
            )

            AddParticipantsCard(
                visible = groupUiModel.addUserToGroupDialogOpen,
                searchParticipantQuery = groupUiModel.searchOrgParticipantsQuery,
                searchParticipantsListState = groupUiModel.searchedOrgParticipants,
                group = groupUiModel.group.group,
                addParticipant = { onEvent(GroupEvents.OnAddUserToGroup(it.id)); onEvent(GroupEvents.OnChangeShowAddUserMenu) },
                searchUsers = { onEvent(GroupEvents.OnSearchOrgParticipants(it)) },
                onDismiss = { onEvent(GroupEvents.OnChangeShowAddUserMenu) }
            )

            groupUiModel.deleteUserAlertVisible?.let { user ->
                DeleteGroupParticipantAlert(
                    userToDeleteName = user.name,
                    onDelete = { onEvent(GroupEvents.OnRemoveUserFromGroup(user.id)); onEvent(GroupEvents.OnRemoveUserFromGroupAlert(null)) },
                    onDismiss = { onEvent(GroupEvents.OnRemoveUserFromGroupAlert(null)) }
                )
            }
            if (groupUiModel.deleteGroupDialogOpen) {
                DeleteGroupAlert(
                    groupName = groupUiModel.group.group.groupName,
                    onSubmit = { onEvent(GroupEvents.OnDeleteGroup) },
                    onDismiss = { onEvent(GroupEvents.OnChangeShowDeleteGroupDialog) }
                )
            }
        }
    }
}