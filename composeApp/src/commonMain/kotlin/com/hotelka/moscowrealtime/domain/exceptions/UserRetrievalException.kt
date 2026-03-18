package com.hotelka.moscowrealtime.domain.exceptions

class UserRetrievalException(cause: Throwable) : Exception("Failed to retrieve user", cause)