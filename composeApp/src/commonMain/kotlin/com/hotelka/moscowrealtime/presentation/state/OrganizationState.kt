package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.Organization

sealed class OrganizationState{
    object Loading: OrganizationState()
    data class Success(val organization: Organization): OrganizationState()
    data class Error(val error: String): OrganizationState()
}