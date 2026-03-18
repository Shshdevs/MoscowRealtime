package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

fun detailDiscover(
    discover: Discover,
    user: User?,
    location: Location?
): DiscoverDetailed = DiscoverDetailed(
    discover = discover,
    userAuthor = user,
    location = location
)