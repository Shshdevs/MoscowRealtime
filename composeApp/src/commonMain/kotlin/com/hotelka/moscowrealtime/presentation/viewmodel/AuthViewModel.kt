package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.GoogleAuthUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.IsUserEmailVerifiedUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.IsUserLoggedInUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.ObserveAuthStateUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.ReloadUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SendChangePasswordLinkUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SendEmailVerificationUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SignInUseCase
import com.hotelka.moscowrealtime.domain.usecase.auth.SignUpUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.UpdateUserTokenUseCase
import com.hotelka.moscowrealtime.presentation.events.AuthGraphEvents
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.mmk.kmpnotifier.notification.NotifierManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val navigator: Navigator,
    isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isUserEmailVerifiedUseCase: IsUserEmailVerifiedUseCase,
    private val observeAuthStateUseCase: ObserveAuthStateUseCase,
    private val reloadUseCase: ReloadUseCase,
    private val sendChangePasswordLinkUseCase: SendChangePasswordLinkUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val googleAuthUseCase: GoogleAuthUseCase,
    private val updateUserTokenUseCase: UpdateUserTokenUseCase
) : ViewModel() {
    private val _showLoading = MutableStateFlow(false)
    val showLoading = _showLoading.asStateFlow()
    private val _isEmailVerified = MutableStateFlow(false)

    fun onEvent(event: AuthGraphEvents) {
        viewModelScope.launch {
            when (event) {

                AuthGraphEvents.NavigateSignIn -> {
                    navigator.navigate(Destination.SignIn.route)
                }

                AuthGraphEvents.NavigateSignUp -> {
                    navigator.navigate(Destination.SignUp.route)
                }

                AuthGraphEvents.OnGoBack -> {
                    navigator.back()
                }

                is AuthGraphEvents.OnResetPassword -> {
                    runCatching { sendChangePasswordLinkUseCase(event.email) }
                }

                is AuthGraphEvents.OnSign -> {
                    _showLoading.update { true }
                    signInUseCase(event.email, event.password).onSuccess {
                        _showLoading.update { false }
                        checkEmailVerification()
                        if (!_isEmailVerified.value) sendEmailVerification()
                        observeEmailVerification()
                        navigator.navigate(Destination.MainGraph.route)
                    }.onFailure { _showLoading.update { false } }
                }

                is AuthGraphEvents.OnSignUp -> {
                    _showLoading.update { true }
                    signUpUseCase(
                        event.email,
                        event.password,
                        event.name,
                        event.username
                    ).onSuccess {
                        _showLoading.update { false }
                        checkEmailVerification()
                        if (!_isEmailVerified.value) sendEmailVerification()
                        observeEmailVerification()
                        navigator.navigate(Destination.MainGraph.route)
                    }.onFailure { _showLoading.update { false } }
                }

                AuthGraphEvents.GoogleSignIn -> {
                    googleAuthUseCase {
                        _showLoading.update { true }
                    }.onSuccess {
                        checkEmailVerification()
                        navigator.navigate(Destination.MainGraph.route)
                    }
                    _showLoading.update { false }
                }
            }

        }
    }

    init {
        observeAuthChanges()
        if (isUserLoggedInUseCase()) {
            checkEmailVerification()
        }
    }

    private fun observeAuthChanges() {
        viewModelScope.launch {
            observeAuthStateUseCase().collect { user ->
                if (user != null) {
                    initNotifier()
                }
            }
        }
    }

    private fun checkEmailVerification() {
        _isEmailVerified.value = isUserEmailVerifiedUseCase() == true
    }

    fun observeEmailVerification() {
        viewModelScope.launch {
            while (!_isEmailVerified.value) {
                reloadUseCase()
                checkEmailVerification()
                delay(5000)
            }
            navigator.navigate(Destination.Home.route)
        }
    }

    fun sendEmailVerification() {
        viewModelScope.launch {
            runCatching { sendEmailVerificationUseCase() }
        }
    }

    private fun initNotifier() {
        getCurrentUserIdUseCase()?.let { userId ->
            viewModelScope.launch {
                NotifierManager.getPushNotifier().getToken()?.let { token ->
                    updateUserTokenUseCase(userId, token)
                }
            }
        }
    }
}