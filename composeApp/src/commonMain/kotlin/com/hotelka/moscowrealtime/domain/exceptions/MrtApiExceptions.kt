package com.hotelka.moscowrealtime.domain.exceptions

class MrtApiExceptions(cause: Throwable) : Exception("MRT API error", cause)
class MrtApiExceptionUnrecognized() : Exception("Unrecognized")