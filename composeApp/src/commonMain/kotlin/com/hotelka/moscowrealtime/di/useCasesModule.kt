package com.hotelka.moscowrealtime.di


import com.hotelka.moscowrealtime.domain.usecase.user.DeleteUserUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserEmailUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.GoogleAuthUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.IsUserEmailVerifiedUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.IsUserLoggedInUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.LogOutUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.ObserveAuthStateUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.ReloadUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SendChangePasswordLinkUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SendEmailVerificationUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SignInUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SignUpUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.DeleteDiscoverUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoverUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversWithIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetUserDiscoversUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.ObserveUserDiscoversUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.AcceptFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.DeclineFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.DeleteFriendUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.GetFriendRequestsDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.ObserveFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.ObserveFriendRequestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.friendRequest.SendFriendRequestUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.AddUserToGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.CreateGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.DeleteGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.GetOrganizationGroupsDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.ObserveOrganizationGroupsUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.PinQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.groups.RemoveUserFromGroupUseCase
import com.hotelka.moscowrealtime.domain.usecase.kudaGo.FetchEventUseCase
import com.hotelka.moscowrealtime.domain.usecase.kudaGo.FetchEventsInRadiusUseCase
import com.hotelka.moscowrealtime.domain.usecase.kudaGo.FetchEventsUseCase
import com.hotelka.moscowrealtime.domain.usecase.locations.GetLocationUseCase
import com.hotelka.moscowrealtime.domain.usecase.locations.GetLocationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.mrtApi.AnalyzeImageUseCase
import com.hotelka.moscowrealtime.domain.usecase.mrtApi.GetClosestLocationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.AcceptOrganizationInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.ChangeOrganizationNameUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.CheckExistingOrganizationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.CreateOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.DeclineOrganizationInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.GetOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.GetOrganizationsBriefDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.InviteUserToOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.ObserveOrganizationInvitationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.ObserveOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.QuitOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.UpdateOrganizationNameUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.AcceptQuestInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.DeclineQuestInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.GetQuestInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.GetQuestInvitationsDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.ObserveQuestInvitationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questInvitations.SendQuestInvitationUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.AddVerificationQuestResultUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.CreateQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressesUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetUserCurrentQuestProgress
import com.hotelka.moscowrealtime.domain.usecase.questProgress.ObserveUserQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.RemoveUserFromQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.RestartQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.CreateQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.DeleteQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestsByIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetUserQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.RemoveParticipantUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.StartQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.AddUserScoreUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.GetUserScoresUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.GetWeeklyTopUsersUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.ObserveUserScoresUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchFriendsUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchLocationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchQuestsUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchUserFriendsUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchUsersUseCase
import com.hotelka.moscowrealtime.domain.usecase.search.SearchUsersWithIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.ContinueQuestUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetCurrUserDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetCurrentUserOrganizationIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetFriendsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUserUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.ObserveUserUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserPrivateSettingsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserProfileInfoUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserProfilePhotoUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserTokenUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetLocationUseCase(get()) }
    factory { GetQuestInvitationUseCase(get()) }
    factory { DeclineQuestInvitationUseCase(get(), get()) }
    factory { ObserveQuestInvitationsUseCase(get(), get()) }
    factory { SendQuestInvitationUseCase(get()) }
    factory { AcceptQuestInvitationUseCase(get(), get(), get(), get()) }
    factory { GetUserCurrentQuestProgress(get(), get()) }
    factory { ObserveUserQuestProgressUseCase(get(), get(), get()) }
    factory { GetQuestProgressUseCase(get()) }
    factory { AddVerificationQuestResultUseCase(get(), get()) }
    factory { CreateQuestProgressUseCase(get()) }
    factory { RestartQuestProgressUseCase(get(), get(), get(), get()) }
    factory { RemoveUserFromQuestProgressUseCase(get()) }
    factory { GetUserCurrentQuestProgress(get(), get()) }
    factory { DeleteFriendUseCase(get(), get(), get()) }
    factory { AcceptFriendRequestUseCase(get(), get()) }
    factory { ObserveFriendRequestUseCase(get(), get()) }
    factory { DeclineFriendRequestUseCase(get()) }
    factory { ObserveFriendRequestsUseCase(get(), get()) }

    factory { SendFriendRequestUseCase(get(), get()) }
    factory { GetLocationsUseCase(get()) }
    factory { GetClosestLocationUseCase(get()) }
    factory { SearchFriendsUseCase(get()) }
    factory { SearchQuestsUseCase(get()) }
    factory { SearchUserFriendsUseCase(get(), get()) }
    factory { SearchUsersUseCase(get(), get()) }
    factory { SearchUsersWithIdsUseCase(get()) }
    factory { SearchLocationsUseCase(get()) }
    factory { AddUserScoreUseCase(get(), get()) }
    factory { GetUserScoresUseCase(get()) }
    factory { GetWeeklyTopUsersUseCase(get()) }
    factory { ObserveUserScoresUseCase(get(), get()) }
    factory { StartQuestUseCase(get(), get(), get(), get()) }
    factory { GetQuestsUseCase(get()) }
    factory { GetQuestUseCase(get()) }
    factory { CreateQuestUseCase(get(), get(), get()) }
    factory { DeleteQuestUseCase(get(), get()) }
    factory { RemoveParticipantUseCase(get()) }
    factory { GetQuestsByIdsUseCase(get()) }
    factory { AnalyzeImageUseCase(get(), get()) }
    factory { FetchEventUseCase(get()) }
    factory { FetchEventsInRadiusUseCase(get()) }
    factory { FetchEventsUseCase(get()) }
    factory { ContinueQuestUseCase(get(), get()) }
    factory { UpdateUserProfilePhotoUseCase(get(), get(), get()) }
    factory { UpdateUserTokenUseCase(get()) }
    factory { GetUsersByIdsUseCase(get()) }
    factory { UpdateUserProfileInfoUseCase(get(), get()) }
    factory { GetCurrentUserOrganizationIdUseCase(get(), get()) }
    factory { GetUserUseCase(get()) }
    factory { UpdateUserPrivateSettingsUseCase(get(), get()) }
    factory { ObserveUserUseCase(get()) }
    factory { GetFriendsUseCase(get()) }
    factory { SignUpUseCase(get(), get()) }
    factory { GetCurrentUserEmailUseCase(get()) }
    factory { SendChangePasswordLinkUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { ReloadUseCase(get()) }
    factory { ObserveAuthStateUseCase(get()) }
    factory { LogOutUseCase(get()) }
    factory { GetCurrentUserIdUseCase(get()) }
    factory { IsUserEmailVerifiedUseCase(get()) }
    factory { SendEmailVerificationUseCase(get()) }
    factory { IsUserLoggedInUseCase(get()) }
    factory { DeleteUserUseCase(get(), get(), get(), get(),get(), get(), get(), get(), get(), get(), get()) }
    factory { GoogleAuthUseCase(get(), get()) }
    factory { GetUserCurrentQuestProgress(get(), get()) }
    factory { GetUserCurrentQuestProgress(get(), get()) }
    factory { RemoveUserFromQuestProgressUseCase(get()) }
    factory { RestartQuestProgressUseCase(get(), get(), get(), get()) }
    factory { AcceptQuestInvitationUseCase(get(), get(), get(), get()) }
    factory { CreateQuestProgressUseCase(get()) }
    factory { AddVerificationQuestResultUseCase(get(), get()) }
    factory { GetQuestProgressUseCase(get()) }
    factory { ObserveUserQuestProgressUseCase(get(), get(), get()) }
    factory { GetQuestProgressesUseCase(get()) }
    factory { GetDiscoverUseCase(get()) }
    factory { GetUserDiscoversUseCase(get()) }
    factory { DeleteDiscoverUseCase(get()) }
    factory { ObserveUserDiscoversUseCase(get(), get()) }
    factory { GetDiscoversWithIdsUseCase(get()) }

    factory { GetUserQuestsUseCase(get()) }
    factory { GetOrganizationUseCase(get()) }
    factory { ObserveOrganizationInvitationsUseCase(get(), get()) }
    factory { ObserveOrganizationUseCase(get(), get(), get()) }
    factory { CreateOrganizationUseCase(get(), get(), get()) }
    factory { InviteUserToOrganizationUseCase(get()) }
    factory { AcceptOrganizationInvitationUseCase(get(), get(), get()) }
    factory { DeclineOrganizationInvitationUseCase(get()) }
    factory { UpdateOrganizationNameUseCase(get()) }
    factory { CheckExistingOrganizationsUseCase(get()) }

    factory { PinQuestUseCase(get()) }
    factory { CreateGroupUseCase(get(), get()) }
    factory { AddUserToGroupUseCase(get()) }
    factory { ObserveOrganizationGroupsUseCase(get()) }
    factory { RemoveUserFromGroupUseCase(get()) }
    factory { ChangeOrganizationNameUseCase(get()) }
    factory { DeleteGroupUseCase(get()) }

    factory { GetOrganizationsBriefDetailsUseCase(get()) }
    factory { GetDiscoversDetailsUseCase(get(), get()) }
    factory { GetFriendRequestsDetailsUseCase(get()) }
    factory { GetCurrUserDetailsUseCase(get(), get(), get(), get(), get(), get()) }
    factory { GetQuestInvitationsDetailsUseCase(get(), get()) }
    factory { QuitOrganizationUseCase(get(), get(), get(), get(), get()) }
    factory { GetQuestProgressDetailsUseCase(get(), get(), get(), get()) }
    factory { GetQuestDetailsUseCase(get(), get(), get(), get(), get(), get(), get()) }
    factory { GetOrganizationGroupsDetailsUseCase(get(), get(), get()) }
}