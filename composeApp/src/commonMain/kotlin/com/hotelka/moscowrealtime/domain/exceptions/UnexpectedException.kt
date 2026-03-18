package com.hotelka.moscowrealtime.domain.exceptions

class UnexpectedException(cause: Throwable) : Exception("Unexpected error", cause)