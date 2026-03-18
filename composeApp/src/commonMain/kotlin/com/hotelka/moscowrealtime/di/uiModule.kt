package com.hotelka.moscowrealtime.di

import com.hotelka.moscowrealtime.domain.routing.RouteBuilder
import com.hotelka.moscowrealtime.presentation.controllers.MapController
import com.hotelka.moscowrealtime.presentation.controllers.MapControllerImpl
import com.hotelka.moscowrealtime.presentation.controllers.MapManager
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.viewmodel.AnalyzeImageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.NetworkMonitorViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.NotificationsHandler
import com.hotelka.moscowrealtime.presentation.viewmodel.CurrentUserProfileViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.DiscoverPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.EventGraphViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.FindFriendsPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.HistoryPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.HomeViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.LibraryViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.OrganizationGraphViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestConstructorPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestProgressGraphViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestsPageViewModel
import com.hotelka.moscowrealtime.presentation.viewmodel.UserProfilePageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    single<Navigator> { Navigator() }
    single<MenuHandler> { MenuHandler(get()) }
    single<MapController> { MapControllerImpl(get()) }
    single<MapManager> { MapManager(get(), RouteBuilder(get())) }
    single { NotificationsHandler(get()) }
    viewModel<NetworkMonitorViewModel> { NetworkMonitorViewModel(get()) }

    viewModel<HomeViewModel> {
        HomeViewModel(
            navigator = get(),
            geoLocationHandler = get(),
            fetchEventsUseCase = get(),
            getClosestLocationUseCase = get(),
        )
    }

    viewModel<HistoryPageViewModel> {
        HistoryPageViewModel(
            navigator = get(),
            observeUserDiscoversUseCase = get(),
            getDiscoversDetailsUseCase = get(),
        )
    }

    viewModel<DiscoverPageViewModel> {
        DiscoverPageViewModel(
            navigator = get(),
            mapManager = get(),
            fetchEventsInRadiusUseCase = get(),
        )
    }

    viewModel<FindFriendsPageViewModel> {
        FindFriendsPageViewModel(
            navigator = get(),
            searchUsersUseCase = get()
        )
    }
    viewModel<QuestProgressGraphViewModel> {
        QuestProgressGraphViewModel(
            observeUserQuestProgressUseCase = get(),
            navigator = get(),
            mapManager = get(),
            getCurrentUserIdUseCase = get(),
            addUserScoreUseCase = get(),
            addVerificationQuestResultUseCase = get(),
            restartQuestUseCase = get(),
            getQuestProgressDetailsUseCase = get()
        )
    }


    viewModel<QuestsPageViewModel> {
        QuestsPageViewModel(
            navigator = get(),
            getQuestsUseCase = get(),
            mapManager = get(),
            observeUserQuestProgressUseCase = get(),
            getQuestProgressDetailsUseCase = get(),
        )
    }
    viewModel<LibraryViewModel> {
        LibraryViewModel(
            navigator = get(),
            getWeeklyTopUsersUseCase = get(),
            getUsersByIdsUseCase = get(),
            searchQuestsUseCase = get(),
            getQuestsUseCase = get()
        )
    }

    viewModel<QuestPageViewModel> {
        QuestPageViewModel(
            navigator = get(),
            getCurrentUserIdUseCase = get(),
            startQuestUseCase = get(),
            continueQuestUseCase = get(),
            searchFriendsUseCase = get(),
            getQuestDetailsUseCase = get(),
            deleteQuestUseCase = get(),
            savedStateHandle = get()
        )
    }

    viewModel<AuthViewModel> {
        AuthViewModel(
            navigator = get(),
            isUserLoggedInUseCase = get(),
            getCurrentUserIdUseCase = get(),
            isUserEmailVerifiedUseCase = get(),
            observeAuthStateUseCase = get(),
            reloadUseCase = get(),
            sendChangePasswordLinkUseCase = get(),
            sendEmailVerificationUseCase = get(),
            signInUseCase = get(),
            signUpUseCase = get(),
            googleAuthUseCase = get(),
            updateUserTokenUseCase = get()
        )
    }

    viewModel<AnalyzeImageViewModel> {
        AnalyzeImageViewModel(
            getCurrentUserIdUseCase = get(),
            analyzeImageUseCase = get(),
            getDiscoversDetailsUseCase = get(),
        )
    }

    viewModel<EventGraphViewModel> {
        EventGraphViewModel(
            navigator = get(),
            menuHandler = get(),
            fetchEventUseCase = get(),
            linkHandler = get(),
            mapManager = get()
        )
    }
    viewModel<OrganizationGraphViewModel> {
        OrganizationGraphViewModel(
            navigator = get(),
            getCurrentUserIdUseCase = get(),
            getCurrentUserOrganizationUseCase = get(),
            observeOrganizationUseCase = get(),
            observeOrganizationGroupsUseCase = get(),
            getOrganizationGroupsDetailsUseCase = get(),
            quitOrganizationUseCase = get(),
            inviteUserToOrganizationUseCase = get(),
            updateOrganizationNameUseCase = get(),
            createGroupUseCase = get(),
            pinQuestUseCase = get(),
            addUserToGroupUseCase = get(),
            removeUserFromGroupUseCase = get(),
            searchQuestsUseCase = get(),
            searchUsersWithIdsUseCase = get(),
            searchUsersUseCase = get(),
            deleteGroupUseCase = get(),
            getUsersByIdsUseCase = get()
        )
    }

    viewModel<CurrentUserProfileViewModel> {
        CurrentUserProfileViewModel(
            navigator = get(),
            getCurrentUserIdUseCase = get(),
            observeUserUseCase = get(),
            observeOrganizationInvitationsUseCase = get(),
            observeQuestInvitationsUseCase = get(),
            getQuestProgressUseCase = get(),
            observeFriendRequestsUseCase = get(),
            observeCurrentQuestProgress = get(),
            observeUserScoresUseCase = get(),
            acceptFriendRequestUseCase = get(),
            declineFriendRequestUseCase = get(),
            acceptQuestInvitationUseCase = get(),
            removeUserFromQuestProgress = get(),
            declineQuestInvitation = get(),
            acceptOrganizationInvitationUseCase = get(),
            declineOrganizationInvitationUseCase = get(),
            updateUserProfileInfoUseCase = get(),
            updateUserProfilePhotoUseCase = get(),
            updateUserPrivateSettingsUseCase = get(),
            changePasswordUseCase = get(),
            deleteAccountUseCase = get(),
            logOutUseCase = get(),
            getOrganizationsBriefDetailsUseCase = get(),
            getFriendRequestsDetailsUseCase = get(),
            getCurrUserDetailsUseCase = get(),
            getQuestProgressDetailsUseCase = get(),
            getQuestInvitationsDetailsUseCase = get(),
            quitOrganizationUseCase = get(),
            createOrganizationUseCase = get(),
            getOrganizationUseCase = get(),
            getUsersByIdsUseCase = get(),
        )
    }

    viewModel<UserProfilePageViewModel> {
        UserProfilePageViewModel(
            navigator = get(),
            getCurrentUserIdUseCase = get(),
            observeUserUseCase = get(),
            getOrganizationUseCase = get(),
            observeFriendRequestUseCase = get(),
            getUserDiscoversUseCase = get(),
            getUserCurrentQuestProgressUseCase = get(),
            getUserScoresUseCase = get(),
            acceptFriendRequestUseCase = get(),
            sendFriendRequestUseCase = get(),
            unFriendUseCase = get(),
            getDiscoversDetailsUseCase = get(),
            getQuestProgressDetailsUseCase = get(),
            getUserQuestsUseCase = get(),
            savedStateHandle = get()
        )
    }

    viewModel<QuestConstructorPageViewModel> {
        QuestConstructorPageViewModel(
            navigator = get(),
            mapManager = get(),
            createQuestUseCase = get(),
            searchLocationsUseCase = get()
        )
    }
}