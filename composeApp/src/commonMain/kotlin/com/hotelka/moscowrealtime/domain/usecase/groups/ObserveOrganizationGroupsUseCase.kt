package com.hotelka.moscowrealtime.domain.usecase.groups

import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow

class ObserveOrganizationGroupsUseCase(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(organizationId: String): Flow<Result<List<Group>>> {
        return groupRepository.observeOrganizationGroups(organizationId)
    }
}