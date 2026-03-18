package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository

class GetOrganizationUseCase(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(organizationId: String): Result<Organization>{
        return organizationRepository.getOrganization(organizationId)
    }
}