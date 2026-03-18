package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.events.CurrentUserProfileEvents
import com.hotelka.moscowrealtime.presentation.events.UserProfileEvents
import com.hotelka.moscowrealtime.presentation.model.UserProfileUiModel
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.UserProfileActions
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.UnfriendUserAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.CurrentQuestCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.OrganizationCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.UserGallery
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.PrivateAccountForm
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.UserProfileHeader
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
fun UserProfileContent(
    uiModel: UserProfileUiModel,
    onEvent: (UserProfileEvents) -> Unit
) {
    Box(Modifier.fillMaxSize().background(background)) {
        LazyColumn(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item("header") {
                UserProfileHeader(
                    user = uiModel.user,
                    scores = uiModel.scores,
                    onScorePillClick = {},
                    showProfilePrivate = uiModel.showProfile || uiModel.showPhotos
                )
            }
            item("content") {
                Column(
                    Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    UserProfileActions(
                        uiModel.user,
                        onDeleteFriend = { onEvent(UserProfileEvents.CallUnFriendAlert) },
                        friendRequestState = uiModel.friendRequestState,
                        sendFriendRequest = { onEvent(UserProfileEvents.OnSendFriendRequest(uiModel.user.id)) },
                        acceptFriendRequest = { onEvent(UserProfileEvents.OnAcceptFriendRequest(it)) },
                    )

                    if (uiModel.organization != null) {
                        OrganizationCard(
                            uiModel.organization,
                            userIsHost = false,
                            showButton = false,
                            navigateOrganizationPage = {},
                        )
                    }
                    if (uiModel.questProgress != null) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(Res.string.my_quests),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = titleTextColor.copy(0.8f),
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                stringResource(Res.string.view_all),
                                fontSize = 16.sp,
                                color = blue,
                                modifier = Modifier
                                    .clip(CircleShape).clickable(onClick = {
                                        onEvent(UserProfileEvents.OnChangeOpenQuestsInvolved)
                                    }).padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                        CurrentQuestCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEvent(UserProfileEvents.OnNavigateQuestPage(it.id)) },
                            questProgressDetailed = uiModel.questProgress,
                        )

                    }
                    if (uiModel.showPhotos) {
                        UserGallery(
                            modifier = Modifier.fillMaxWidth(),
                            gallery = uiModel.discovers,
                            onOpenGallery = { onEvent(UserProfileEvents.OnChangeOpenGalleryDiscovers) },
                            onOpenDiscover = { onEvent(UserProfileEvents.OnChangeOpenDiscover(it)) }
                        )
                    } else {
                        PrivateAccountForm(!uiModel.showProfile)
                    }
                }
            }

            item("spacer") {
                Spacer(Modifier.height(200.dp))
            }
        }
        SlideVerticallyCardAnim(
            visible = uiModel.showQuestsInvolved
        ) {
            QuestsInvolvedCard(
                modifier = Modifier.padding(top = 150.dp).fillMaxSize(),
                quests = uiModel.questsInvolved,
                onOpenQuest = {quest -> onEvent(UserProfileEvents.OnNavigateQuestPage(quest.id))},
                onDismiss = {onEvent(UserProfileEvents.OnChangeOpenQuestsInvolved)}
            )
        }
        SlideVerticallyCardAnim(
            visible = uiModel.isGalleryOpen,
            content = {
                UserGalleryListCard(
                    Modifier.padding(top = 150.dp).fillMaxSize(),
                    onOpenDiscover = { onEvent(UserProfileEvents.OnChangeOpenDiscover(it)) },
                    gallery = uiModel.discovers,
                    onDismiss = { onEvent(UserProfileEvents.OnChangeOpenGalleryDiscovers) }
                )
            }
        )
        SlideVerticallyCardAnim(
            visible = uiModel.openDiscover != null,
            content = {
                uiModel.openDiscover?.let {
                    DiscoverPage(
                        discoverDetailed = it,
                        onDismiss = { onEvent(UserProfileEvents.OnChangeOpenDiscover(null)) }
                    )
                }
            }
        )
        if (uiModel.showUnFriendAlert) {
            UnfriendUserAlert(
                user = uiModel.user,
                onSubmit = { onEvent(UserProfileEvents.OnUnFriend(uiModel.user)) },
                onDismiss = { onEvent(UserProfileEvents.CallUnFriendAlert) }
            )
        }
    }
}