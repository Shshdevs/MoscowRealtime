package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.FriendRequestDto
import com.hotelka.moscowrealtime.domain.model.FriendRequest

fun FriendRequest.toDto(): FriendRequestDto = FriendRequestDto(
    userTo = userTo,
    userFrom = userFrom,
    status = status,
    sent = sent
)

fun FriendRequestDto.toDomain(): FriendRequest = FriendRequest(
    userTo = userTo,
    userFrom = userFrom,
    status = status,
    sent = sent
)