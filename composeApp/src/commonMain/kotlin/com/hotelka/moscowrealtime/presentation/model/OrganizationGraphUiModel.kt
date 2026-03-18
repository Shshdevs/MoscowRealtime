package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.domain.model.Organizer
import com.hotelka.moscowrealtime.presentation.state.OrganizationState

data class OrganizationGraphUiModel(
    val orgId: String = "",
    val organization: OrganizationState = OrganizationState.Loading,
    val organizers: List<Organizer> = emptyList(),
    val organizationGroups: List<GroupDetailed> = emptyList(),
    val currUserGroups: List<GroupDetailed> = emptyList(),
    val participants: List<User> = emptyList(),
    val currUserIsHost: Boolean = false,
    val homePageUiModel: OrgHomePageUiModel = OrgHomePageUiModel(currUserIsHost),
    val navigatedGroupPageUiModel: GroupPageUiModel? = null,
    val navigatedOrganizerPageUiModel: OrganizerPageUiModel? = null,
    )