package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.OrganizationInvitationDto
import com.hotelka.moscowrealtime.domain.exceptions.UserInvitedException
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class OrganizationInvitationDataSourceImpl(
    private val firestore: FirebaseFirestore
) : OrganizationInvitationDataSource {


    override suspend fun createInvitation(invitation: OrganizationInvitationDto): Result<Unit> =
        try {
            if (!checkUserNotInvited(invitation.userTo, invitation.organizationId)){
                Result.failure(UserInvitedException())
            } else {
                val docRef =
                    firestore.collection(ORGANIZATIONS_INVITATIONS_COLLECTION).add(invitation)
                docRef.update(mapOf("id" to docRef.id))
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error creating invitation", e))
        }

    override suspend fun deleteInvitation(invitationId: String): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_INVITATIONS_COLLECTION).document(invitationId).delete()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting invitation", e))
    }

    override suspend fun observeOrganizationInvitations(userId: String): Flow<Result<List<OrganizationInvitationDto>>> =
        firestore.collection(ORGANIZATIONS_INVITATIONS_COLLECTION).where {
            "userTo" equalTo userId
        }.snapshots.map { snapshot ->
            try {
                val invitations = snapshot.documents.map { it.data<OrganizationInvitationDto>() }
                Result.success(invitations)
            } catch (e: Exception) {
                Result.failure(Exception("Error observing invitations", e))
            }
        }.catch { e ->
            emit(Result.failure(Exception("Error observing invitations", e)))
        }

    private suspend fun checkUserNotInvited(userId: String, organizationId: String): Boolean {
        return firestore.collection(ORGANIZATIONS_INVITATIONS_COLLECTION).where {
            ("userTo" equalTo userId).and("organizationId" equalTo organizationId)
        }.get().documents.isEmpty()
    }
    companion object {
        private const val ORGANIZATIONS_INVITATIONS_COLLECTION = "organizations_invitations"
    }
}