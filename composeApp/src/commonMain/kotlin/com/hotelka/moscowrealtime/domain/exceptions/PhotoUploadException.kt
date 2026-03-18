package com.hotelka.moscowrealtime.domain.exceptions

class PhotoUploadException(cause: Throwable) : Exception("Failed to upload photo", cause)

