package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import com.hotelka.moscowrealtime.domain.repository.GroupRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class DeleteUserUseCase(
    private val questInvitationsRepository: QuestInvitationRepository,
    private val friendsRequestRepository: FriendRequestRepository,
    private val questProgressRepository: QuestProgressRepository,
    private val questRepository: QuestRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val organizationRepository: OrganizationRepository,
    private val groupRepository: GroupRepository,
    private val scoreRepository: ScoreRepository,
    private val getUserOrganizationUseCase: GetCurrentUserOrganizationIdUseCase,
    private val discoversRepository: DiscoveriesRepository
) {
    suspend operator fun invoke(): Result<Unit> = runCatching{
        val userId = authRepository.getCurrentUserId()?: return Result.failure(Exception(UserNotAuthenticatedException()))
        questInvitationsRepository.removeUserFromInvitations(userId)
        friendsRequestRepository.removeFriendInvitationsForUser(userId)
        questRepository.removeFromParticipants(userId)
        questProgressRepository.removeUserFromQuestProgresses(userId)
        scoreRepository.deleteUsersScores(userId)
        discoversRepository.deleteUserDiscovers(userId)

        val organizationId = getUserOrganizationUseCase()
        organizationId?.let {
            organizationRepository.removeUserFromOrganization(userId, it)
            organizationRepository.removeUserHostFromOrganization(userId, it)
            groupRepository.deleteUserFromOrganizationGroups(userId, it)
        }
        userRepository.deleteUser(userId)
        authRepository.deleteUser()
        authRepository.logout()
        Result.success(Unit)
    }
}