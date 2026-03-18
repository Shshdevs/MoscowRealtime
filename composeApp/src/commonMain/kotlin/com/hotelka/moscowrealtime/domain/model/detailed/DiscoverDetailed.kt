package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.User

data class DiscoverDetailed(
    val discover: Discover,
    val userAuthor: User?,
    val location: Location?
)