package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.DiscoverDto
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DiscoveriesDataSourceImpl(
    private val firestore: FirebaseFirestore
) : DiscoveriesDataSource {
    override suspend fun getDiscover(discoverId: String): Result<DiscoverDto> = try {
        val doc = firestore.collection(DISCOVERIES_COLLECTION).document(discoverId).get()
        Result.success(doc.data<DiscoverDto>())
    }catch (e: Exception) {
        Result.failure(Exception("Error getting discover with id $discoverId", e))
    }

    override suspend fun getDiscoversByIds(discoverIds: List<String>): Result<List<DiscoverDto>> = try {
        val docs = firestore.collection(DISCOVERIES_COLLECTION).where {
            "__name__" inArray discoverIds
        }.get().documents
        Result.success(docs.map { it.data<DiscoverDto>() })
    } catch (e: Exception){
        Result.failure(Exception("Error getting discoveries", e))
    }

    override suspend fun gerUserDiscoveries(userId: String): Result<List<DiscoverDto>> = try {
        val results = firestore.collection(DISCOVERIES_COLLECTION).where {
            FieldPath("userAuthor") equalTo userId
        }.get().documents.map { it.data<DiscoverDto>() }
        Result.success(results)
    } catch (e: Exception) {
        Result.failure(Exception("Error getting discoveries", e))
    }

    override suspend fun observeUserDiscoveries(userId: String): Flow<Result<List<DiscoverDto>>> =
        firestore.collection(DISCOVERIES_COLLECTION).where {
            FieldPath("userAuthor") equalTo userId
        }.snapshots
            .map { collector ->
                try {
                    Result.success(collector.documents.map { doc -> doc.data<DiscoverDto>() })
                } catch (e: Exception) {
                    Result.failure(Exception("Error observing discoveries", e))
                }
            }
            .catch { e ->
                emit(Result.failure(Exception("Error observing discoveries", e)))
            }

    override suspend fun deleteUserDiscover(discoveryId: String): Result<Unit> = try {
        firestore.collection(DISCOVERIES_COLLECTION).document(discoveryId).delete()
        Result.success(Unit)
    }catch (e: Exception){
        Result.failure(Exception("Error deleting discovery", e))
    }

    override suspend fun deleteUserDiscovers(userId: String): Result<Unit> = try {
        firestore.collection(DISCOVERIES_COLLECTION).where {
            FieldPath("userAuthor") equalTo userId
        }.get().documents.forEach { it.reference.delete() }
        Result.success(Unit)
    }catch (e: Exception){
        Result.failure(Exception("Error deleting discovers", e))
    }

    companion object Companion {
        private const val DISCOVERIES_COLLECTION = "discoveries"
    }
}