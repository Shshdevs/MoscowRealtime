package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.CurrUserDetailed
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserEmailUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetUserDiscoversUseCase
import com.hotelka.moscowrealtime.domain.usecase.organizations.GetOrganizationUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetUserQuestsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetCurrUserDetailsUseCase(
    private val getOrganizationUseCase: GetOrganizationUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getUserDiscoversUseCase: GetUserDiscoversUseCase,
    private val getUserDiscoversDetailsUseCase: GetDiscoversDetailsUseCase,
    private val getCurrentUserEmailUseCase: GetCurrentUserEmailUseCase,
    private val getUserQuestsUseCase: GetUserQuestsUseCase
) {
    suspend operator fun invoke(user: User): CurrUserDetailed {
        return coroutineScope {
            val organizationDeferred =
                user.organizationId?.let { async { getOrganizationUseCase(it).getOrNull() } }
            val discoversDeferred =
                async { getUserDiscoversUseCase(user.id).getOrDefault(emptyList()) }
            val userQuestsDeferred = async {
                getUserQuestsUseCase(user.id).getOrDefault(emptyList())
            }
            val userQuests = userQuestsDeferred.await()
            val friends = getUsersByIdsUseCase(user.friends).getOrDefault(emptyList())
            val organization = organizationDeferred?.await()
            val discovers = discoversDeferred.await()

            val userDetailed = CurrUserDetailed(
                user = user,
                organization = organization,
                discovers = getUserDiscoversDetailsUseCase(discovers),
                friends = friends,
                email = getCurrentUserEmailUseCase() ?: "",
                questsInvolved = userQuests
            )
            userDetailed
        }
    }
}