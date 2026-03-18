package com.hotelka.moscowrealtime.presentation.events

sealed class AuthGraphEvents {
    object OnGoBack: AuthGraphEvents()
    object NavigateSignIn: AuthGraphEvents()
    object NavigateSignUp: AuthGraphEvents()
    object GoogleSignIn: AuthGraphEvents()
    data class OnResetPassword(val email: String): AuthGraphEvents()
    data class OnSign(val email: String, val password: String): AuthGraphEvents()
    data class OnSignUp(val email: String, val password: String, val name: String, val username: String): AuthGraphEvents()
}