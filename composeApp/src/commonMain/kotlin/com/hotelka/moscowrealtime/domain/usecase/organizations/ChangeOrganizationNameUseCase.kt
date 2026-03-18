package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository

class ChangeOrganizationNameUseCase(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(organizationId: String, name: String): Result<Unit> {
        return organizationRepository.changeOrganizationName(organizationId, name)
    }
}