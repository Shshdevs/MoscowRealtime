package com.hotelka.moscowrealtime.domain.model

data class OrganizationInvitation(
    val id: String,
    val organizationId: String,
    val toBeHost: Boolean,
    val userTo: String,
)