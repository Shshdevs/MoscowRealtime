package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.sunildhiman90.kmauth.google.KMAuthGoogle
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow

class AuthDataSourceImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthDataSource {

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun observeAuthState(): Flow<FirebaseUser?> {
        return firebaseAuth.authStateChanged
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    override suspend fun deleteUser(): Result<Boolean> = try {
        val user = firebaseAuth.currentUser
            ?: return Result.failure(Exception(UserNotAuthenticatedException()))

        user.delete()
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<FirebaseUser> =
        runCatching {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .user ?: error("Error authentication user")
        }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<FirebaseUser> =
        runCatching {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .user ?: error("Error signing in")
        }

    override suspend fun googleAuth(): Result<FirebaseUser> =
        runCatching {
            val googleUser = GOOGLE_AUTH_MANAGER
                .signIn()
                .getOrThrow()
                ?: error("Google auth canceled")

            val credential = GoogleAuthProvider.credential(
                googleUser.idToken,
                googleUser.accessToken
            )
            firebaseAuth
                .signInWithCredential(credential)
                .user
                ?: error("Google auth failed")
        }

    override suspend fun logout() {
        Firebase.auth.signOut()
    }

    override suspend fun sendEmailVerification() {
        firebaseAuth.currentUser?.sendEmailVerification()
    }

    override suspend fun reload() {
        firebaseAuth.currentUser?.reload()
    }

    override fun isUserEmailVerified(): Boolean? {
        return firebaseAuth.currentUser?.isEmailVerified
    }

    override suspend fun sendChangePasswordLink(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    companion object {
        private val GOOGLE_AUTH_MANAGER = KMAuthGoogle.googleAuthManager
    }
}