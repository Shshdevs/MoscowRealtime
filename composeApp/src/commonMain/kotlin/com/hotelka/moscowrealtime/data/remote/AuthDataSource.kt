package com.hotelka.moscowrealtime.data.remote

import com.sunildhiman90.kmauth.google.GoogleUser
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun isUserLoggedIn(): Boolean
    fun observeAuthState(): Flow<FirebaseUser?>
    fun getCurrentUserId(): String?
    fun getCurrentUserEmail(): String?
    suspend fun deleteUser(): Result<Boolean>
    suspend fun signUp(email: String, password: String): Result<FirebaseUser>
    suspend fun signIn(email: String, password: String): Result<FirebaseUser>
    suspend fun googleAuth(): Result<FirebaseUser>
    suspend fun logout()
    suspend fun sendEmailVerification()
    suspend fun reload()
    fun isUserEmailVerified(): Boolean?
    suspend fun sendChangePasswordLink(email: String)
}