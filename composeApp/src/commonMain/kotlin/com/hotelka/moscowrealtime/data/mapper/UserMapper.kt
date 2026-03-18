package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.UserDto
import com.hotelka.moscowrealtime.domain.model.User

fun User.toDto(): UserDto {
    return UserDto(
        id = id,
        username = username,
        name = name,
        name_lowered = name.lowercase(),
        userPic = userPic,
        token = token,
        organizationId = organizationId,
        currentQuestProgress = currentQuestProgress,
        profilePrivate = profilePrivate,
        photosPrivate = photosPrivate,
        friends = friends,
    )
}

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        name = name,
        userPic = userPic,
        token = token,
        organizationId = organizationId,
        currentQuestProgress = currentQuestProgress,
        profilePrivate = profilePrivate,
        photosPrivate = photosPrivate,
        friends = friends,
    )
}