package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.OrganizationHomePageEvents
import com.hotelka.moscowrealtime.presentation.model.OrganizationGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.OrganizationState
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.QuitOrganizationButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.ActionRow
import com.hotelka.moscowrealtime.presentation.ui.Content.items.InviteUserItem
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.change_organization_name
import moscowrealtime.composeapp.generated.resources.edit
import moscowrealtime.composeapp.generated.resources.group
import moscowrealtime.composeapp.generated.resources.invite_client
import moscowrealtime.composeapp.generated.resources.invite_organizer
import moscowrealtime.composeapp.generated.resources.loading_organization
import moscowrealtime.composeapp.generated.resources.loading_organization_error
import moscowrealtime.composeapp.generated.resources.lol
import moscowrealtime.composeapp.generated.resources.manager
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrganizationSettingsForm(
    organizationPageUiState: OrganizationGraphUiModel,
    onEvent: (OrganizationHomePageEvents) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        when (organizationPageUiState.organization) {
            is OrganizationState.Error -> {
                item {
                    ErrorForm(
                        Modifier.fillMaxSize(),
                        errorDescription = Res.string.loading_organization_error,
                        onRetry = { onEvent(OrganizationHomePageEvents.OnReload) }
                    )
                }
            }

            OrganizationState.Loading -> {
                item {
                    LoadingForm(
                        modifier = Modifier.fillMaxSize(),
                        loadingAction = stringResource(Res.string.loading_organization)
                    )
                }
            }

            is OrganizationState.Success -> {
                if (organizationPageUiState.currUserIsHost) {
                    item {
                        DefaultAppCard(
                            Modifier.padding(top = 5.dp),
                            onClick = { onEvent(OrganizationHomePageEvents.OnChangeShowOrgNameDialog) }
                        ) {
                            ActionRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                icon = Res.drawable.edit,
                                info = (Res.string.change_organization_name)
                            )
                        }
                    }
                    item {
                        DefaultAppCard(
                            onClick = { onEvent(OrganizationHomePageEvents.OnChangeShowInviteOrganizer) }
                        ) {
                            ActionRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                icon = Res.drawable.manager,
                                info = if (organizationPageUiState.homePageUiModel.actionInviteOrganizerVisible) Res.string.cancel else Res.string.invite_organizer
                            )
                        }
                    }
                    item {
                        DefaultAppCard(
                            onClick = { onEvent(OrganizationHomePageEvents.OnChangeShowInviteClient) }
                        ) {
                            ActionRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                icon = Res.drawable.group,
                                info = if (organizationPageUiState.homePageUiModel.actionInviteClientVisible) Res.string.cancel else Res.string.invite_client
                            )
                        }
                    }
                    if (organizationPageUiState.homePageUiModel.actionInviteClientVisible || organizationPageUiState.homePageUiModel.actionInviteOrganizerVisible) {
                        item {
                            AnimatedVisibility(
                                visible = organizationPageUiState.homePageUiModel.actionInviteClientVisible || organizationPageUiState.homePageUiModel.actionInviteOrganizerVisible,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                SearchUsersForm(
                                    modifier = Modifier.size(400.dp),
                                    finalQuery = organizationPageUiState.homePageUiModel.searchUsersQuery,
                                    usersListState = organizationPageUiState.homePageUiModel.searchedUsers,
                                    searchUsers = {
                                        onEvent(OrganizationHomePageEvents.OnSearchUsers(it))
                                    },
                                    userSearchItem = { user ->
                                        InviteUserItem(
                                            Modifier.fillMaxWidth(),
                                            if (organizationPageUiState.homePageUiModel.actionInviteOrganizerVisible) Res.string.invite_organizer
                                            else if (organizationPageUiState.homePageUiModel.actionInviteClientVisible) Res.string.invite_client else Res.string.lol,
                                            user
                                        ) {
                                            if (organizationPageUiState.homePageUiModel.actionInviteClientVisible) {
                                                onEvent(
                                                    OrganizationHomePageEvents.OnInvite(
                                                        user.id,
                                                        false
                                                    )
                                                )
                                            } else if (organizationPageUiState.homePageUiModel.actionInviteOrganizerVisible) {
                                                onEvent(
                                                    OrganizationHomePageEvents.OnInvite(
                                                        user.id,
                                                        true
                                                    )
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            AnimatedVisibility(
                                organizationPageUiState.homePageUiModel.message != null,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                DefaultAppCard(onClick = null) {
                                    Text(
                                        organizationPageUiState.homePageUiModel.message?.let {
                                            stringResource(
                                                it
                                            )
                                        } ?: "",
                                        Modifier.padding(20.dp).fillMaxWidth()
                                            .animateContentSize(),
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = secondaryTextColor
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    QuitOrganizationButton { onEvent(OrganizationHomePageEvents.OnChangeShowQuitOrgDialog) }
                }
                item { Spacer(Modifier.height(40.dp)) }
            }
        }
    }
}