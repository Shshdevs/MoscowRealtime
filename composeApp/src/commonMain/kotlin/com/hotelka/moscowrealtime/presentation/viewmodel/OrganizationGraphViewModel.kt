package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.exceptions.UserInvitedException
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.AddUserToGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.CreateGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.DeleteGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.GetOrganizationGroupsDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.ObserveOrganizationGroupsUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.PinQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.RemoveUserFromGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.InviteUserToOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.ObserveOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.QuitOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.UpdateOrganizationNameUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchUsersUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchUsersWithIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetCurrentUserOrganizationIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.presentation.events.GroupEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationGraphEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationHomePageEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizationNavEvents
import com.hotelka.moscowrealtime.presentation.events.OrganizerPageEvents
import com.hotelka.moscowrealtime.presentation.model.GroupPageUiModel
import com.hotelka.moscowrealtime.presentation.model.OrgHomePageUiModel
import com.hotelka.moscowrealtime.presentation.model.OrganizationGraphUiModel
import com.hotelka.moscowrealtime.presentation.model.OrganizerPageUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.OrganizationState
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState
import com.hotelka.moscowrealtime.presentation.state.listState.UsersListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.adding_participant
import moscowrealtime.composeapp.generated.resources.client_invited
import moscowrealtime.composeapp.generated.resources.inviting
import moscowrealtime.composeapp.generated.resources.organizer_invited
import moscowrealtime.composeapp.generated.resources.participant_added
import moscowrealtime.composeapp.generated.resources.participant_removed
import moscowrealtime.composeapp.generated.resources.pinning_quest
import moscowrealtime.composeapp.generated.resources.quest_is_pinned
import moscowrealtime.composeapp.generated.resources.removing_participant
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import moscowrealtime.composeapp.generated.resources.user_already_invited
import org.jetbrains.compose.resources.StringResource

class OrganizationGraphViewModel(
    private val navigator: Navigator,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCurrentUserOrganizationUseCase: GetCurrentUserOrganizationIdUseCase,
    private val observeOrganizationUseCase: ObserveOrganizationUseCase,
    private val observeOrganizationGroupsUseCase: ObserveOrganizationGroupsUseCase,
    private val getOrganizationGroupsDetailsUseCase: GetOrganizationGroupsDetailsUseCase,
    private val quitOrganizationUseCase: QuitOrganizationUseCase,
    private val inviteUserToOrganizationUseCase: InviteUserToOrganizationUseCase,
    private val updateOrganizationNameUseCase: UpdateOrganizationNameUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val pinQuestUseCase: PinQuestUseCase,
    private val addUserToGroupUseCase: AddUserToGroupUseCase,
    private val removeUserFromGroupUseCase: RemoveUserFromGroupUseCase,
    private val searchQuestsUseCase: SearchQuestsUseCase,
    private val searchUsersWithIdsUseCase: SearchUsersWithIdsUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase
) : ViewModel() {
    private val currUserId: String? get() = getCurrentUserIdUseCase()
    private val _organizationGraphUiModel = MutableStateFlow(OrganizationGraphUiModel())
    val organizationGraphUiModel = _organizationGraphUiModel.asStateFlow()

    init {
        loadOrganizationGraphData()
    }

    fun onEvent(event: OrganizationGraphEvents) {
        when (event) {
            is GroupEvents -> handleGroupPageEvent(event)
            is OrganizerPageEvents -> handleOrganizerPageEvent(event)
            is OrganizationHomePageEvents -> handleHomePageEvents(event)
            is OrganizationNavEvents -> handleOrgGraphNavigation(event)
        }
    }

    private fun handleHomePageEvents(event: OrganizationHomePageEvents) {
        val orgHomePageUiModel = _organizationGraphUiModel.value.homePageUiModel
        fun updateHomePage(orgHomePageUiModel: OrgHomePageUiModel) {
            _organizationGraphUiModel.update { uiModel -> uiModel.copy(homePageUiModel = orgHomePageUiModel) }
        }

        fun setMessage(message: StringResource?) =
            updateHomePage(orgHomePageUiModel.copy(message = message))

        suspend fun setMessageError() {
            setMessage(Res.string.something_went_wrong); delay(3_000L); setMessage(null)
        }
        when (event) {
            OrganizationHomePageEvents.OnChangeShowInviteClient -> {
                updateHomePage(
                    orgHomePageUiModel.copy(
                        actionInviteClientVisible = !orgHomePageUiModel.actionInviteClientVisible,
                        actionInviteOrganizerVisible = false
                    )
                )
            }

            OrganizationHomePageEvents.OnChangeShowInviteOrganizer -> {
                updateHomePage(
                    orgHomePageUiModel.copy(
                        actionInviteOrganizerVisible = !orgHomePageUiModel.actionInviteOrganizerVisible,
                        actionInviteClientVisible = false
                    )
                )
            }

            OrganizationHomePageEvents.OnChangeShowOrgNameDialog -> {
                updateHomePage(orgHomePageUiModel.copy(showChangeNameDialog = !orgHomePageUiModel.showChangeNameDialog))
            }

            OrganizationHomePageEvents.OnChangeShowQuitOrgDialog -> {
                updateHomePage(orgHomePageUiModel.copy(showQuitOrgDialog = !orgHomePageUiModel.showQuitOrgDialog))
            }

            is OrganizationHomePageEvents.OnInvite -> {
                viewModelScope.launch {
                    setMessage(Res.string.inviting)
                    inviteUserToOrganizationUseCase(
                        _organizationGraphUiModel.value.orgId,
                        event.toBeHost,
                        event.userId
                    ).onSuccess {
                        setMessage(if (event.toBeHost) Res.string.organizer_invited else Res.string.client_invited)
                        delay(3_000L)
                        setMessage(null)
                    }.onFailure { e ->
                        if (e is UserInvitedException) {
                            setMessage(Res.string.user_already_invited)
                            delay(3_000L)
                            setMessage(null)
                        } else {
                            setMessageError()
                        }
                    }
                }
            }

            is OrganizationHomePageEvents.OnChangeOrgName -> {
                viewModelScope.launch {
                    updateOrganizationNameUseCase(
                        _organizationGraphUiModel.value.orgId,
                        event.name
                    ).onSuccess {
                        updateHomePage(orgHomePageUiModel.copy(showChangeNameDialog = false))
                    }
                }
            }

            OrganizationHomePageEvents.OnReload -> {
                loadOrganizationGraphData()
            }

            OrganizationHomePageEvents.OnQuitOrg -> {
                viewModelScope.launch {
                    quitOrganizationUseCase().onSuccess {
                        navigator.back()
                    }
                }
            }

            is OrganizationHomePageEvents.OnSearchUsers -> {
                viewModelScope.launch {
                    updateHomePage(
                        orgHomePageUiModel.copy(
                            searchUsersQuery = event.query,
                            searchedUsers = UsersListState.Loading
                        )
                    )
                    val usersListState = searchUsersUseCase(event.query).fold(
                        onSuccess = { users -> UsersListState.Success(users) },
                        onFailure = { e -> UsersListState.Error(e.message.toString()) }
                    )
                    updateHomePage(orgHomePageUiModel.copy(searchedUsers = usersListState))

                }

            }
        }
    }

    private fun handleOrganizerPageEvent(event: OrganizerPageEvents) {
        val currOrganizerPageUiModel =
            _organizationGraphUiModel.value.navigatedOrganizerPageUiModel ?: return

        fun updateOrganizerPageState(uiModel: OrganizerPageUiModel) {
            _organizationGraphUiModel.update { orgUiModel ->
                Logger.withTag("Create group dialog").d {
                    orgUiModel.navigatedOrganizerPageUiModel.toString()
                }
                orgUiModel.copy(navigatedOrganizerPageUiModel = uiModel)
            }
        }

        when (event) {
            is OrganizerPageEvents.OnAddGroup -> {
                viewModelScope.launch {
                    val orgId = getCurrentUserOrganizationUseCase() ?: return@launch
                    createGroupUseCase(orgId, event.groupName).onFailure {
                        handleOrganizerPageEvent(OrganizerPageEvents.OnShowCreateGroupDialogError)
                    }
                }
            }

            OrganizerPageEvents.OnChangeShowCreateGroupDialog -> {
                updateOrganizerPageState(
                    currOrganizerPageUiModel.copy(createGroupDialogOpen = !currOrganizerPageUiModel.createGroupDialogOpen)
                )
            }

            OrganizerPageEvents.OnShowCreateGroupDialogError -> {
                updateOrganizerPageState(
                    currOrganizerPageUiModel.copy(
                        errorCreatingGroupDialogOpen = !currOrganizerPageUiModel.errorCreatingGroupDialogOpen
                    )
                )
            }
        }
    }

    private fun handleGroupPageEvent(event: GroupEvents) {
        val currGroupPageUiModel =
            _organizationGraphUiModel.value.navigatedGroupPageUiModel ?: return

        fun updateGroupPage(groupPageUiModel: GroupPageUiModel) {

            _organizationGraphUiModel.update { uiModel -> uiModel.copy(navigatedGroupPageUiModel = groupPageUiModel) }
        }

        when (event) {
            GroupEvents.OnChangeEditGroup -> {
                updateGroupPage(currGroupPageUiModel.copy(editGroupModeOn = !currGroupPageUiModel.editGroupModeOn))
            }

            GroupEvents.OnChangeOpenPinQuestMenu -> {
                updateGroupPage(currGroupPageUiModel.copy(pinQuestDialogOpen = !currGroupPageUiModel.pinQuestDialogOpen))
            }

            GroupEvents.OnChangeShowAddUserMenu -> {
                updateGroupPage(currGroupPageUiModel.copy(addUserToGroupDialogOpen = !currGroupPageUiModel.addUserToGroupDialogOpen))
            }

            GroupEvents.OnChangeShowDeleteGroupDialog -> {
                updateGroupPage(currGroupPageUiModel.copy(deleteGroupDialogOpen = !currGroupPageUiModel.deleteGroupDialogOpen))
            }

            GroupEvents.OnChangeShowRemoveUserDialog -> {
                updateGroupPage(currGroupPageUiModel.copy(removeUserFromGroupDialogOpen = !currGroupPageUiModel.removeUserFromGroupDialogOpen))
            }

            is GroupEvents.OnPinQuest -> {
                viewModelScope.launch {
                    pinQuestUseCase(
                        currGroupPageUiModel.group.group.id,
                        event.questId
                    )
                }
            }

            is GroupEvents.OnAddUserToGroup -> {
                viewModelScope.launch {
                    addUserToGroupUseCase(
                        event.userId,
                        currGroupPageUiModel.group.group.id
                    )
                }
            }

            is GroupEvents.OnRemoveUserFromGroup -> {
                viewModelScope.launch {
                    removeUserFromGroupUseCase(
                        event.userId,
                        currGroupPageUiModel.group.group.id
                    )
                }
            }

            is GroupEvents.OnSearchOrgParticipants -> {
                viewModelScope.launch {
                    updateGroupPage(
                        currGroupPageUiModel.copy(
                            searchOrgParticipantsQuery = event.query,
                            searchedOrgParticipants = UsersListState.Loading
                        )
                    )
                    val searchedUsersState = searchUsersWithIdsUseCase(
                        event.query,
                        _organizationGraphUiModel.value.participants.map { it.id }).fold(
                        onSuccess = { users -> UsersListState.Success(users.filter { it.id !in currGroupPageUiModel.group.users.map { user -> user.user.id } }) },
                        onFailure = { e -> UsersListState.Error(e.message.toString()) }
                    )
                    updateGroupPage(
                        currGroupPageUiModel.copy(
                            searchedOrgParticipants = searchedUsersState
                        )
                    )
                }
            }

            is GroupEvents.OnSearchQuests -> {
                viewModelScope.launch {
                    updateGroupPage(
                        currGroupPageUiModel.copy(
                            searchQuestsQuery = event.query,
                            searchedQuests = QuestsListState.Loading
                        )
                    )
                    val searchedQuestsState = searchQuestsUseCase(event.query).fold(
                        onSuccess = { quests -> QuestsListState.Success(quests) },
                        onFailure = { e -> QuestsListState.Error(e.message.toString()) }
                    )
                    updateGroupPage(
                        currGroupPageUiModel.copy(
                            searchedQuests = searchedQuestsState
                        )
                    )
                }
            }

            is GroupEvents.OnRemoveUserFromGroupAlert -> {
                updateGroupPage(currGroupPageUiModel.copy(deleteUserAlertVisible = event.user))
            }

            GroupEvents.OnDeleteGroup -> {
                viewModelScope.launch {
                    navigator.back()
                    deleteGroupUseCase(currGroupPageUiModel.group.group.id)
                }
            }
        }
    }

    private fun handleOrgGraphNavigation(event: OrganizationNavEvents) {
        when (event) {
            OrganizationNavEvents.OnNavigateBack -> {
                navigator.back()
            }

            is OrganizationNavEvents.OnNavigateGroup -> {
                _organizationGraphUiModel.update { uiModel ->
                    uiModel.copy(
                        navigatedGroupPageUiModel = GroupPageUiModel(
                            group = event.group,
                            allowCRUD = currUserId == event.group.organizer.id
                        )
                    )
                }
                navigator.navigate(Destination.GroupPage.route)
            }

            is OrganizationNavEvents.OnNavigateOrganizerPage -> {
                _organizationGraphUiModel.update { uiModel ->
                    uiModel.copy(
                        navigatedOrganizerPageUiModel = OrganizerPageUiModel(
                            organizer = event.organizer,
                            allowCRUD = event.organizer.organizer.id == currUserId
                        )
                    )
                }
                navigator.navigate(Destination.OrganizerPage.route)
            }

            is OrganizationNavEvents.OnNavigateQuestPage -> {
                navigator.navigate(Destination.QuestPage.create(event.questId))
            }

            OrganizationNavEvents.NavigateMyGroups -> {
                if (!organizationGraphUiModel.value.currUserIsHost) {
                    navigator.navigate(Destination.ClientGroups.route)
                } else {
                    val currUserAsOrganizer =
                        organizationGraphUiModel.value.organizers.first { it.organizer.id == currUserId }
                    _organizationGraphUiModel.update { uiModel ->
                        uiModel.copy(
                            navigatedOrganizerPageUiModel = OrganizerPageUiModel(
                                organizer = currUserAsOrganizer,
                                allowCRUD = true
                            )
                        )
                    }
                    navigator.navigate(Destination.OrganizerPage.route)
                }
            }
        }
    }

    private fun loadOrganizationGraphData() {
        viewModelScope.launch {
            val orgId = getCurrentUserOrganizationUseCase() ?: return@launch
            combine(
                observeOrganizationUseCase(),
                observeOrganizationGroupsUseCase(orgId)
            ) { organization, groups ->
                val organization = organization.getOrThrow()
                val groupsResult = groups.getOrDefault(emptyList())
                val (groups, organizers) = getOrganizationGroupsDetailsUseCase(
                    organization,
                    groupsResult
                )
                val currUserIsHost = organizers.map { it.organizer.id }.contains(currUserId)
                val participants =
                    getUsersByIdsUseCase(organization.users).getOrDefault(emptyList())
                OrganizationGraphUiModel(
                    orgId,
                    OrganizationState.Success(organization),
                    organizers = organizers,
                    organizationGroups = groups,
                    currUserGroups =
                        if (currUserIsHost) {
                            organizers.filter { it.organizer.id == currUserId }
                                .getOrNull(0)?.groups.orEmpty()
                        } else {
                            groups.filter { group ->
                                group.users.map { it.user.id }.contains(currUserId)
                            }
                        },
                    participants = participants,
                    currUserIsHost = currUserIsHost
                )
            }.catch { throwable ->
                _organizationGraphUiModel.update { uiModel ->
                    uiModel.copy(organization = OrganizationState.Error(throwable.message.toString()))
                }
            }.collect { uiModel ->
                _organizationGraphUiModel.update { _uiModel ->
                    val navigatedOrganizerPageUiModel =
                        uiModel.organizers.firstOrNull { organizer -> _uiModel.navigatedOrganizerPageUiModel?.organizer?.organizer?.id == organizer.organizer.id }
                            ?.let { _uiModel.navigatedOrganizerPageUiModel?.copy(organizer = it) }

                    val navigatedGroupPageUiModel =
                        uiModel.organizationGroups.firstOrNull() { groupDetailed ->_uiModel.navigatedGroupPageUiModel?.group?.group?.id == groupDetailed.group.id }
                            ?.let { _uiModel.navigatedGroupPageUiModel?.copy(group = it) }

                    _uiModel.copy(
                        orgId = orgId,
                        organization = uiModel.organization,
                        organizers = uiModel.organizers,
                        organizationGroups = uiModel.organizationGroups,
                        currUserGroups = uiModel.currUserGroups,
                        participants = uiModel.participants,
                        currUserIsHost = uiModel.currUserIsHost,
                        homePageUiModel = _uiModel.homePageUiModel.copy(currUserIsHost = uiModel.currUserIsHost),
                        navigatedGroupPageUiModel = navigatedGroupPageUiModel,
                        navigatedOrganizerPageUiModel = navigatedOrganizerPageUiModel
                    )
                }
            }
        }
    }
}