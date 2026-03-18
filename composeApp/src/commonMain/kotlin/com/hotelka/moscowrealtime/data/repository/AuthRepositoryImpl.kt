package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.AuthDataSource
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override fun isUserLoggedIn(): Boolean {
        return authDataSource.isUserLoggedIn()
    }

    override fun observeAuthState(): Flow<FirebaseUser?> {
        return authDataSource.observeAuthState()
    }

    override fun getCurrentUserId(): String? {
        return authDataSource.getCurrentUserId()
    }

    override fun getCurrentUserEmail(): String? {
        return authDataSource.getCurrentUserEmail()
    }

    override suspend fun deleteUser(): Result<Boolean> {
        return authDataSource.deleteUser()
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return authDataSource.signUp(email, password)
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return authDataSource.signIn(email, password)
    }

    override suspend fun googleAuth(): Result<FirebaseUser> {
        return authDataSource.googleAuth()
    }

    override suspend fun logout() {
        authDataSource.logout()
    }

    override suspend fun sendEmailVerification() {
        authDataSource.sendEmailVerification()
    }

    override suspend fun reload() {
        authDataSource.reload()
    }

    override fun isUserEmailVerified(): Boolean? {
        return authDataSource.isUserEmailVerified()
    }

    override suspend fun sendChangePasswordLink(email: String) {
        authDataSource.sendEmailVerification()
    }
}