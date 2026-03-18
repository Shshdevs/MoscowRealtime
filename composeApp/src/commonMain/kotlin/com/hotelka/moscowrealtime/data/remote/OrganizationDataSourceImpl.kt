package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.OrganizationDto
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class OrganizationDataSourceImpl(
    private val firestore: FirebaseFirestore
) : OrganizationDataSource {

    override suspend fun createOrganization(organization: OrganizationDto): Result<String> = try {
        val ref = firestore.collection(ORGANIZATIONS_COLLECTION).add(organization)
        ref.update(mapOf("id" to ref.id))
        Result.success(ref.id)
    } catch (e: Exception) {

        Result.failure(Exception("Error creating organization", e))
    }

    override suspend fun checkExistingOrganizations(name: String): Result<List<String>> = try {
        val keywords = name.lowercase()
            .split(" ", ",", ".", "-", "_")
            .filter { it.length > 2 }
            .distinct()
        if (keywords.size <= 3) {
            Result.success(emptyList())
        } else {
            val organizations = firestore.collection(ORGANIZATIONS_COLLECTION).where {
                FieldPath("search_keywords") containsAny keywords
            }.get().documents.map { it.data<OrganizationDto>() }

            Result.success(organizations.map { it.name })
        }
    } catch (e: Exception) {
        Logger.withTag("Check name org").d { e.cause.toString() }
        Result.failure(Exception("Error checking organizations exist", e))
    }

    override suspend fun addUserToOrganization(
        userId: String,
        organizationId: String
    ): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION).document(organizationId).update(
            FieldPath("users") to FieldValue.arrayUnion(userId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error add user to organization", e))
    }

    override suspend fun addUserHostToOrganization(
        userHostId: String,
        organizationId: String
    ): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION).document(organizationId).update(
            FieldPath("usersHosts") to FieldValue.arrayUnion(userHostId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error add user host to organization", e))
    }


    override suspend fun removeUserFromOrganization(
        userId: String,
        organizationId: String
    ): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION).document(organizationId).update(
            FieldPath("users") to FieldValue.arrayRemove(userId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error remove user to organization", e))
    }

    override suspend fun removeUserHostFromOrganization(
        userHostId: String,
        organizationId: String
    ): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION).document(organizationId).update(
            FieldPath("usersHosts") to FieldValue.arrayRemove(userHostId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error remove user host to organization", e))
    }

    override suspend fun updateOrganizationName(
        organizationId: String,
        name: String
    ): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION).document(organizationId)
            .update(mapOf("name" to name))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error update organization name", e))
    }


    override suspend fun observeOrganization(organizationId: String): Flow<Result<OrganizationDto>> =
        firestore.collection(ORGANIZATIONS_COLLECTION)
            .document(organizationId).snapshots.map { snapshot ->
                val organization = snapshot.data<OrganizationDto>()
                Result.success(organization)
            }
            .catch { e ->

                emit(Result.failure(e))
            }

    override suspend fun getOrganization(organizationId: String): Result<OrganizationDto> = try {
        val data = firestore.collection(ORGANIZATIONS_COLLECTION)
            .document(organizationId)
            .get().data<OrganizationDto>()
        Result.success(data)
    } catch (e: Exception) {
        Result.failure(Exception("Error getting organization", e))
    }

    override suspend fun getOrganizations(organizationIds: List<String>): Result<List<OrganizationDto>> =
        try {
            if (organizationIds.isEmpty()) {
                Result.success(emptyList())
            } else {
                val organizations = firestore.collection(ORGANIZATIONS_COLLECTION).where {
                    "__name__" inArray organizationIds
                }.get().documents.map { it.data<OrganizationDto>() }
                Result.success(organizations)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error getting organizations", e))
        }

    override suspend fun deleteOrganization(organizationId: String): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION).document(organizationId).delete()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error delete organization", e))
    }

    override suspend fun changeOrganizationName(
        organizationId: String,
        name: String
    ): Result<Unit> = try {
        firestore.collection(ORGANIZATIONS_COLLECTION)
            .document(organizationId).update(FieldPath("name") to name)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error renaming organization", e))
    }

    companion object {
        private const val ORGANIZATIONS_COLLECTION = "organizations"
    }
}