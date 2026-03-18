package com.hotelka.moscowrealtime.domain.usecase.organizations

import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository

class CheckExistingOrganizationsUseCase(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(name: String): Result<List<String>> {
        return organizationRepository.checkExistingOrganizations(name)
    }
}