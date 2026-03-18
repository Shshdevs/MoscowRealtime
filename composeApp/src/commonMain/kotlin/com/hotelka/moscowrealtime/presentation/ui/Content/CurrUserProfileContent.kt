package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.CurrentUserProfileEvents
import com.hotelka.moscowrealtime.presentation.model.CurrUserProfileUiModel
import com.hotelka.moscowrealtime.presentation.state.OrganizationInvitationsStateList
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.UserAchievements
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.FindFriendsButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.CurrentQuestCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.EditProfileInfoCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.EditSettingsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.OrganizationCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.OrganizationInvitationCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.UserGallery
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.CurrUserProfileAlertHolder
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.CurrUserProfileHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.listCards.FriendsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.listCards.FriendsRequestsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.listCards.QuestInvitationsCard
import com.hotelka.moscowrealtime.presentation.ui.Content.listCards.QuestsInvolvedCard
import com.hotelka.moscowrealtime.presentation.ui.Content.listCards.UserGalleryListCard
import com.hotelka.moscowrealtime.presentation.ui.screen.DiscoverPage
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.my_quests
import moscowrealtime.composeapp.generated.resources.view_all
import org.jetbrains.compose.resources.stringResource

@Composable
fun CurrUserProfileContent(
    uiModel: CurrUserProfileUiModel,
    onEvent: (CurrentUserProfileEvents) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item("header") {
            CurrUserProfileHeader(
                user = uiModel.userDetailed.user,
                tempUserInfo = uiModel.tempUser,
                userScore = uiModel.userDetailed.scores,
                onOpenSettings = { onEvent(CurrentUserProfileEvents.OnChangeOpenSettings) },
                onScorePillClick = {}
            )
        }
        item("content") {
            Column(
                Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                UserAchievements(
                    user = uiModel.userDetailed.user,
                    friendRequests = uiModel.friendRequests,
                    openFriends = { onEvent(CurrentUserProfileEvents.OnChangeOpenFriends) },
                    openFriendRequests = { onEvent(CurrentUserProfileEvents.OnChangeOpenFriendRequests) },
                    openQuestInvitations = { onEvent(CurrentUserProfileEvents.OnChangeOpenQuestInvitations) },
                    questInvitations = uiModel.questInvitations,
                )
                FindFriendsButton({ onEvent(CurrentUserProfileEvents.FindFriends) })

                if ((uiModel.organizationInvitations is OrganizationInvitationsStateList.Success) && uiModel.organizationInvitations.invitationsUiModels.isNotEmpty()) {
                    OrganizationInvitationCard(
                        organizationInvitation = uiModel.organizationInvitations.invitationsUiModels[0],
                        onAccept = {
                            if (uiModel.userDetailed.organization != null) {
                                onEvent(CurrentUserProfileEvents.OnShowChangeOrgAlert(it))
                            } else {
                                onEvent(CurrentUserProfileEvents.AcceptOrganizationInvitation(it))
                            }
                        },
                        onDecline = {
                            onEvent(
                                CurrentUserProfileEvents.DeclineOrganizationInvitation(
                                    it
                                )
                            )
                        },
                    )
                }

                if (uiModel.userDetailed.organization != null) {
                    OrganizationCard(
                        organization = uiModel.userDetailed.organization,
                        userIsHost = uiModel.userIsOrganizationHost,
                        showButton = true,
                        navigateOrganizationPage = { onEvent(CurrentUserProfileEvents.NavigateOrganization) }
                    )
                }
                if (uiModel.userDetailed.questProgress != null) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            stringResource(Res.string.my_quests),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = titleTextColor.copy(0.8f),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            stringResource(Res.string.view_all),
                            fontSize = 16.sp,
                            color = blue,
                            modifier = Modifier
                                .clip(CircleShape).clickable(onClick = {
                                    onEvent(CurrentUserProfileEvents.OnChangeOpenQuestsInvolved)
                                }).padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                    CurrentQuestCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { quest -> onEvent(CurrentUserProfileEvents.NavigateQuest(quest.id)) },
                        questProgressDetailed = uiModel.userDetailed.questProgress,
                    )
                }
                UserGallery(
                    modifier = Modifier.fillMaxWidth(),
                    onOpenGallery = { onEvent(CurrentUserProfileEvents.OnChangeOpenDiscoversGallery) },
                    onOpenDiscover = { onEvent(CurrentUserProfileEvents.OnChangeOpenDiscover(it)) },
                    gallery = uiModel.userDetailed.discovers,
                )
            }
        }
        item("spacer") {
            Spacer(Modifier.height(200.dp))
        }
    }

    SlideVerticallyCardAnim(
        visible = uiModel.isSettingsOpen,
        content = {
            EditSettingsCard(
                modifier = Modifier.padding(top = 160.dp).fillMaxSize(),
                user = uiModel.userDetailed.user,
                onChangePassword = { onEvent(CurrentUserProfileEvents.OnChangeShowChangePasswordAlert) },
                onLogOut = { onEvent(CurrentUserProfileEvents.OnChangeShowLogOutAlert) },
                onEditProfile = { onEvent(CurrentUserProfileEvents.OnChangeOpenEditProfileInfo) },
                onDeleteAccount = { onEvent(CurrentUserProfileEvents.OnChangeShowDeleteAccountAlert) },
                onDismissRequest = { onEvent(CurrentUserProfileEvents.OnChangeOpenSettings) },
                onCreateOrganization = { onEvent(CurrentUserProfileEvents.OnShowCreateOrganizationAlert) },
                updatePrivateSettings = { profilePrivate, photosPrivate ->
                    onEvent(
                        CurrentUserProfileEvents.UpdateProfileSettings(
                            profilePrivate,
                            photosPrivate
                        )
                    )
                }
            )
        }
    )
    SlideVerticallyCardAnim(
        visible = uiModel.isQuestsInvolvedOpen,
        content = {
            QuestsInvolvedCard(
                modifier = Modifier.padding(top = 140.dp).fillMaxSize(),
                quests = uiModel.userDetailed.questsInvolved,
                onOpenQuest = { quest -> onEvent(CurrentUserProfileEvents.NavigateQuest(quest.id)) },
                onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenQuestsInvolved) }
            )
        }
    )
    SlideVerticallyCardAnim(
        visible = uiModel.isFriendsOpen,
        content = {
            FriendsCard(
                Modifier.padding(top = 140.dp).fillMaxSize(),
                friends = uiModel.userDetailed.friends,
                navigateFindFriends = { onEvent(CurrentUserProfileEvents.FindFriends) },
                onViewProfile = { onEvent(CurrentUserProfileEvents.NavigateUser(it)) },
                onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenFriends) }
            )
        }
    )
    SlideVerticallyCardAnim(
        visible = uiModel.isFriendRequestsOpen,
        content = {
            FriendsRequestsCard(
                Modifier.padding(top = 140.dp).fillMaxSize(),
                onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenFriendRequests) },
                friendsRequestsUiState = uiModel.friendRequests,
                acceptFriendRequest = { onEvent(CurrentUserProfileEvents.AcceptFriendRequest(it)) },
                declineFriendRequest = { onEvent(CurrentUserProfileEvents.DeclineFriendRequest(it)) },
                navigateUserProfile = { onEvent(CurrentUserProfileEvents.NavigateUser(it)) },
                onRetryLoadFriendRequests = { onEvent(CurrentUserProfileEvents.OnReload) }
            )
        }
    )
    SlideVerticallyCardAnim(
        visible = uiModel.isQuestInvitationsOpen,
        content = {
            QuestInvitationsCard(
                Modifier.padding(top = 140.dp).fillMaxSize(),
                onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenQuestInvitations) },
                questInvitationsUiState = uiModel.questInvitations,
                navigateQuest = { quest -> onEvent(CurrentUserProfileEvents.NavigateQuest(quest.id)) },
                acceptQuestInvitation = { onEvent(CurrentUserProfileEvents.AcceptQuestInvitation(it)) },
                declineQuestInvitation = {
                    onEvent(CurrentUserProfileEvents.DeclineQuestInvitation(it))
                }
            )
        }
    )
    SlideVerticallyCardAnim(
        visible = uiModel.isDiscoversGalleryOpen,
        content = {
            UserGalleryListCard(
                Modifier.padding(top = 150.dp).fillMaxSize(),
                gallery = uiModel.userDetailed.discovers,
                onOpenDiscover = { onEvent(CurrentUserProfileEvents.OnChangeOpenDiscover(it)) },
                onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenDiscoversGallery) },
            )
        }
    )
    SlideVerticallyCardAnim(
        visible = uiModel.isEditProfileInfoOpen,
        content = {
            EditProfileInfoCard(
                modifier = Modifier.padding(top = 280.dp).fillMaxSize(),
                tempUser = uiModel.tempUser,
                onSubmit = { onEvent(CurrentUserProfileEvents.UpdateProfileInfo) },
                updateTempUser = { onEvent(CurrentUserProfileEvents.UpdateTempUser(it)) },
                onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenEditProfileInfo) },
                pickPhoto = { onEvent(CurrentUserProfileEvents.OnPickPhoto) },
            )
        }
    )

    SlideVerticallyCardAnim(
        visible = uiModel.openDiscover != null,
        content = {
            uiModel.openDiscover?.let {
                DiscoverPage(
                    onDismiss = { onEvent(CurrentUserProfileEvents.OnChangeOpenDiscover(null)) },
                    discoverDetailed = it,
                )
            }
        }
    )
    CurrUserProfileAlertHolder(
        createOrganization = uiModel.showCreateOrganizationAlert,
        deleteAccountAlert = uiModel.showDeleteAccountAlert,
        changePassword = uiModel.showChangePasswordAlert,
        logOut = uiModel.showLogOutAlert,
        email = uiModel.userDetailed.email,
        changeOrg = uiModel.showChangeOrgAlert != null,
        acceptQuestInvitationAlert = uiModel.restartingQuestAlert != null,
        onRestartProgress = {
            uiModel.restartingQuestAlert?.let {
                onEvent(
                    CurrentUserProfileEvents.AcceptQuestInvitation(
                        it
                    )
                )
            }
        },
        onDeleteAccount = { onEvent(CurrentUserProfileEvents.OnDeleteAccount) },
        onChangePassword = { onEvent(CurrentUserProfileEvents.OnChangePassword(it)) },
        onLogOut = { onEvent(CurrentUserProfileEvents.OnLogOut) },
        onChangeOrg = { onEvent(CurrentUserProfileEvents.OnSubmitChangeOrgAlert) },
        onCreateOrganization = { onEvent(CurrentUserProfileEvents.OnCreateOrganization(it)) },
        onClose = { onEvent(CurrentUserProfileEvents.OnCloseAlert) },
    )
}