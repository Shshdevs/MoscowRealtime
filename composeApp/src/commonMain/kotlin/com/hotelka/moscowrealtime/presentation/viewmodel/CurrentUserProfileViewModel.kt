package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.FriendRequest
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.TempUserInfo
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.LogOutUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SendChangePasswordLinkUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.AcceptFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.DeclineFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.GetFriendRequestsDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.ObserveFriendRequestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.AcceptOrganizationInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.CreateOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.DeclineOrganizationInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.GetOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.GetOrganizationsBriefDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.ObserveOrganizationInvitationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.QuitOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.AcceptQuestInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.DeclineQuestInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.GetQuestInvitationsDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.ObserveQuestInvitationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.ObserveUserQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.RemoveUserFromQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.ObserveUserScoresUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.DeleteUserUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetCurrUserDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.ObserveUserUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserPrivateSettingsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserProfileInfoUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserProfilePhotoUseCase
import com.hotelka.moscowrealtime.presentation.events.CurrentUserProfileEvents
import com.hotelka.moscowrealtime.presentation.events.CurrentUserProfileEvents.OnShowChangeOrgAlert
import com.hotelka.moscowrealtime.presentation.events.CurrentUserProfileEvents.OnShowCreateOrganizationAlert
import com.hotelka.moscowrealtime.presentation.model.CurrUserProfileUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.CurrentUserProfileUiState
import com.hotelka.moscowrealtime.presentation.state.FriendsRequestsUiState
import com.hotelka.moscowrealtime.presentation.state.OrganizationInvitationsStateList
import com.hotelka.moscowrealtime.presentation.state.listState.QuestInvitationsUiState
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray
import io.github.vinceglb.filekit.dialogs.compose.util.toImageBitmap
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrentUserProfileViewModel(
    private val navigator: Navigator,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val observeUserUseCase: ObserveUserUseCase,
    private val getOrganizationsBriefDetailsUseCase: GetOrganizationsBriefDetailsUseCase,
    private val getFriendRequestsDetailsUseCase: GetFriendRequestsDetailsUseCase,
    private val observeOrganizationInvitationsUseCase: ObserveOrganizationInvitationsUseCase,
    private val createOrganizationUseCase: CreateOrganizationUseCase,
    private val getCurrUserDetailsUseCase: GetCurrUserDetailsUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val observeQuestInvitationsUseCase: ObserveQuestInvitationsUseCase,
    private val observeFriendRequestsUseCase: ObserveFriendRequestsUseCase,
    private val observeCurrentQuestProgress: ObserveUserQuestProgressUseCase,
    private val observeUserScoresUseCase: ObserveUserScoresUseCase,
    private val getQuestProgressDetailsUseCase: GetQuestProgressDetailsUseCase,
    private val getQuestInvitationsDetailsUseCase: GetQuestInvitationsDetailsUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val declineFriendRequestUseCase: DeclineFriendRequestUseCase,
    private val acceptQuestInvitationUseCase: AcceptQuestInvitationUseCase,
    private val removeUserFromQuestProgress: RemoveUserFromQuestProgressUseCase,
    private val getQuestProgressUseCase: GetQuestProgressUseCase,
    private val declineQuestInvitation: DeclineQuestInvitationUseCase,
    private val acceptOrganizationInvitationUseCase: AcceptOrganizationInvitationUseCase,
    private val declineOrganizationInvitationUseCase: DeclineOrganizationInvitationUseCase,
    private val updateUserProfileInfoUseCase: UpdateUserProfileInfoUseCase,
    private val updateUserProfilePhotoUseCase: UpdateUserProfilePhotoUseCase,
    private val updateUserPrivateSettingsUseCase: UpdateUserPrivateSettingsUseCase,
    private val changePasswordUseCase: SendChangePasswordLinkUseCase,
    private val deleteAccountUseCase: DeleteUserUseCase,
    private val quitOrganizationUseCase: QuitOrganizationUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {
    private val _currentUserProfileUiState =
        MutableStateFlow<CurrentUserProfileUiState>(CurrentUserProfileUiState.None)
    val currentUserProfileUiState = _currentUserProfileUiState.asStateFlow()

    private val currUserId: String? get() = getCurrentUserIdUseCase()

    init {
        loadCurrentUser()
    }

    fun onEvent(event: CurrentUserProfileEvents) {
        viewModelScope.launch {
            when (event) {
                is CurrentUserProfileEvents.AcceptFriendRequest -> {
                    acceptFriendRequestUseCase(event.request)
                }

                is CurrentUserProfileEvents.AcceptOrganizationInvitation -> {
                    acceptOrganizationInvitationUseCase(event.invitation)
                }

                is CurrentUserProfileEvents.AcceptQuestInvitation -> {
                    val currUserId = currUserId ?: return@launch
                    _currentUserProfileUiState.update { uiState ->
                        val successState = uiState as CurrentUserProfileUiState.Success
                        if (successState.uiModel.restartingQuestAlert == null) {
                            getQuestProgressUseCase(currUserId, event.invitation.questId).fold(
                                onSuccess = { questProgress ->
                                    if (questProgress != null) {
                                        successState.copy(
                                            uiModel = successState.uiModel.copy(
                                                restartingQuestAlert = event.invitation
                                            )
                                        )
                                    } else {
                                        acceptQuestInvitationUseCase(currUserId, event.invitation)
                                        successState
                                    }
                                },
                                onFailure = { e ->
                                    successState
                                }
                            )
                        } else {
                            removeUserFromQuestProgress(currUserId, event.invitation.questId)
                            acceptQuestInvitationUseCase(currUserId, event.invitation)
                            successState.copy(
                                uiModel = successState.uiModel.copy(
                                    restartingQuestAlert = null
                                )
                            )
                        }
                    }
                }

                is CurrentUserProfileEvents.DeclineFriendRequest -> {
                    declineFriendRequestUseCase(event.request)
                }

                is CurrentUserProfileEvents.DeclineOrganizationInvitation -> {
                    declineOrganizationInvitationUseCase(event.invitation)
                }

                is CurrentUserProfileEvents.DeclineQuestInvitation -> {
                    declineQuestInvitation(event.invitation)
                }

                is CurrentUserProfileEvents.OnReload -> {
                    loadCurrentUser()
                    observeUserData()
                }

                CurrentUserProfileEvents.OnPickPhoto -> {
                    val img = FileKit.openFilePicker(FileKitType.Image)
                    img?.toImageBitmap()?.let { img ->
                        _currentUserProfileUiState.update { uiState ->
                            (uiState as CurrentUserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(
                                    tempUser = uiState.uiModel.tempUser?.copy(
                                        tryPic = img
                                    )
                                )
                            )
                        }
                    }

                }

                CurrentUserProfileEvents.UpdateProfileInfo -> {
                    val tempUser =
                        (_currentUserProfileUiState.value as CurrentUserProfileUiState.Success).uiModel.tempUser
                    tempUser?.let { tempUser ->
                        tempUser.tryPic?.let { updateUserProfilePhotoUseCase(it.encodeToByteArray()) }
                        updateUserProfileInfoUseCase(tempUser.name, tempUser.username)
                        _currentUserProfileUiState.update { uiState ->
                            (uiState as CurrentUserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(
                                    tempUser = null, isEditProfileInfoOpen = false,
                                    isSettingsOpen = true
                                )
                            )
                        }
                    }
                }

                is CurrentUserProfileEvents.UpdateProfileSettings -> {
                    updateUserPrivateSettingsUseCase(event.profilePrivate, event.photosPrivate)
                }

                CurrentUserProfileEvents.FindFriends -> {
                    navigator.navigate(Destination.SearchUsersPage.route)
                }

                CurrentUserProfileEvents.NavigateOrganization -> {
                    navigator.navigate(Destination.OrganizationGraph.route)
                }

                is CurrentUserProfileEvents.NavigateQuest -> {
                    navigator.navigate(Destination.QuestPage.create(event.questId))
                }

                is CurrentUserProfileEvents.NavigateUser -> {
                    navigator.navigate(Destination.UserProfilePage.create(event.user.id))
                }

                CurrentUserProfileEvents.OnChangeOpenFriendRequests -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                isFriendRequestsOpen = !uiState.uiModel.isFriendRequestsOpen
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeOpenFriends -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(isFriendsOpen = !uiState.uiModel.isFriendsOpen)
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeOpenSettings -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(isSettingsOpen = !uiState.uiModel.isSettingsOpen)
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeOpenDiscoversGallery -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                isDiscoversGalleryOpen = !uiState.uiModel.isDiscoversGalleryOpen
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeOpenEditProfileInfo -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = (if (!uiState.uiModel.isEditProfileInfoOpen) {
                                val user = uiState.uiModel.userDetailed.user
                                uiState.uiModel.copy(
                                    tempUser = TempUserInfo(user.name, user.username, null)
                                )
                            } else uiState.uiModel).copy(
                                isSettingsOpen = uiState.uiModel.isEditProfileInfoOpen,
                                isEditProfileInfoOpen = !uiState.uiModel.isEditProfileInfoOpen
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeOpenQuestInvitations -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                isQuestInvitationsOpen = !uiState.uiModel.isQuestInvitationsOpen
                            )
                        )
                    }
                }

                is CurrentUserProfileEvents.OnChangeOpenDiscover -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                openDiscover = event.discover
                            )
                        )
                    }
                }

                is CurrentUserProfileEvents.UpdateTempUser -> {
                    event.tempUser?.let { tempUser ->
                        _currentUserProfileUiState.update { uiState ->
                            (uiState as CurrentUserProfileUiState.Success).copy(
                                uiModel = uiState.uiModel.copy(tempUser = tempUser)
                            )
                        }
                    }
                }

                is CurrentUserProfileEvents.OnChangePassword -> {
                    changePasswordUseCase(event.email)
                }

                CurrentUserProfileEvents.OnChangeShowChangePasswordAlert -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                showChangePasswordAlert = true
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeShowDeleteAccountAlert -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                showDeleteAccountAlert = true
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnChangeShowLogOutAlert -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                showLogOutAlert = true
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnCloseAlert -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                showDeleteAccountAlert = false,
                                showLogOutAlert = false,
                                showChangePasswordAlert = false,
                                showCreateOrganizationAlert = false,
                                showChangeOrgAlert = null
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnDeleteAccount -> {
                    deleteAccountUseCase().onSuccess {
                        navigator.seeYouNextTime()
                    }
                }

                CurrentUserProfileEvents.OnLogOut -> {
                    runCatching { logOutUseCase() }.onSuccess {
                        navigator.navigateLogOut()
                    }
                }

                OnShowCreateOrganizationAlert -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                showCreateOrganizationAlert = true
                            )
                        )
                    }
                }

                is OnShowChangeOrgAlert -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(
                                showChangeOrgAlert = event.organizationInvitation
                            )
                        )
                    }
                }

                CurrentUserProfileEvents.OnSubmitChangeOrgAlert -> {
                    val invitation =
                        (_currentUserProfileUiState.value as CurrentUserProfileUiState.Success).uiModel.showChangeOrgAlert
                    invitation?.let { invitation ->
                        viewModelScope.launch {
                            quitOrganizationUseCase().onSuccess {
                                acceptOrganizationInvitationUseCase(invitation).onSuccess {
                                    onEvent(OnShowChangeOrgAlert(null))
                                }
                            }
                        }
                    }
                }

                is CurrentUserProfileEvents.OnCreateOrganization -> {
                    createOrganizationUseCase(event.orgName)
                }

                CurrentUserProfileEvents.OnChangeOpenQuestsInvolved -> {
                    _currentUserProfileUiState.update { uiState ->
                        (uiState as CurrentUserProfileUiState.Success).copy(
                            uiModel = uiState.uiModel.copy(isQuestsInvolvedOpen = !uiState.uiModel.isQuestsInvolvedOpen)
                        )
                    }
                }
            }
        }
    }

    private fun loadCurrentUser() {
        val userId = currUserId ?: return
        if (_currentUserProfileUiState.value is CurrentUserProfileUiState.None) {
            _currentUserProfileUiState.value = CurrentUserProfileUiState.Loading
        }
        viewModelScope.launch {
            observeUserUseCase(userId).collect { result ->
                result.fold(
                    onSuccess = { user ->
                        val uiModelState = _currentUserProfileUiState.value

                        if (uiModelState !is CurrentUserProfileUiState.Success) {
                            val userDetailed = getCurrUserDetailsUseCase(user)
                            _currentUserProfileUiState.value =
                                CurrentUserProfileUiState.Success(
                                    CurrUserProfileUiModel(userDetailed)
                                )
                            observeUserData()
                        } else {
                            _currentUserProfileUiState.value =
                                CurrentUserProfileUiState.Success(
                                    uiModelState.uiModel.copy(
                                        userDetailed = uiModelState.uiModel.userDetailed.copy(
                                            friends = getUsersByIdsUseCase(user.friends).getOrDefault(
                                                emptyList()
                                            ),
                                            organization = user.organizationId?.let {
                                                getOrganizationUseCase(
                                                    it
                                                ).getOrNull()
                                            },
                                            user = user
                                        )
                                    )
                                )
                        }
                    },
                    onFailure = { e ->
                        _currentUserProfileUiState.value =
                            CurrentUserProfileUiState.Error(e.message.toString())
                    }
                )
            }
        }
    }

    private fun observeUserData() {
        viewModelScope.launch {
            combine(
                observeFriendRequestsUseCase(),
                observeQuestInvitationsUseCase(),
                observeOrganizationInvitationsUseCase(),
                observeCurrentQuestProgress(),
                observeUserScoresUseCase()
            ) { friendsRequests, questInvitations, organizationsInvitations, questProgress, scores ->
                Observers(
                    friendsRequests.getOrDefault(listOf()),
                    questInvitations.getOrDefault(listOf()),
                    organizationsInvitations.getOrDefault(listOf()),
                    questProgress.getOrNull(),
                    scores.getOrDefault(emptyList())
                )
            }.catch { e ->
                Logger.withTag("Exception curr user").e { e.message.toString() }
            }
                .collect { observers ->
                    val friendRequests = getFriendRequestsDetailsUseCase(observers.friendRequests)
                    val questInvitations =
                        getQuestInvitationsDetailsUseCase(observers.questInvitations)
                    val organizationsInvitations =
                        getOrganizationsBriefDetailsUseCase(observers.organizationInvitations)
                    val questProgress =
                        observers.questProgress?.let { getQuestProgressDetailsUseCase(it) }
                    val scores = observers.scores

                    _currentUserProfileUiState.update { uiState ->
                        if (uiState is CurrentUserProfileUiState.Success) {
                            uiState.copy(
                                uiState.uiModel.copy(
                                    questInvitations = QuestInvitationsUiState.Success(
                                        questInvitations
                                    ),
                                    friendRequests = FriendsRequestsUiState.Success(friendRequests),
                                    organizationInvitations = OrganizationInvitationsStateList.Success(
                                        organizationsInvitations
                                    ),
                                    userDetailed = uiState.uiModel.userDetailed.copy(
                                        questProgress = questProgress,
                                        scores = scores
                                    )
                                )
                            )
                        } else {
                            uiState
                        }
                    }
                }
        }
    }


    companion object {
        private data class Observers(
            val friendRequests: List<FriendRequest>,
            val questInvitations: List<QuestInvitation>,
            val organizationInvitations: List<OrganizationInvitation>,
            val questProgress: QuestProgress?,
            val scores: List<Score>
        )
    }
}