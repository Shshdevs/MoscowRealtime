package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.LocationDto
import dev.gitlive.firebase.firestore.FirebaseFirestore

class LocationDataSourceImpl(
    private val firestore: FirebaseFirestore
) : LocationDataSource {
    override suspend fun getLocation(locationId: String): Result<LocationDto>  = try {
        val docRef = firestore.collection(LOCATIONS_COLLECTION).document(locationId).get()
        Result.success(docRef.data<LocationDto>())
    } catch (e: Exception){
        Result.failure(Exception("Error getting location with id $locationId", e))
    }

    override suspend fun getLocationsByIds(locationIds: List<String>): Result<List<LocationDto>> = try {
        if (locationIds.isEmpty()){
            Result.success(emptyList())
        }else {
            val docRefsMap = firestore.collection(LOCATIONS_COLLECTION).where {
                "__name__" inArray locationIds
            }.get().documents.associateBy { it.id }
            val docRefs = locationIds.mapNotNull { docRefsMap[it] }
            Result.success(docRefs.map { it.data<LocationDto>() })
        }
    } catch (e: Exception){
        Result.failure(Exception("Error getting locations", e))
    }
    companion object{
        private const val LOCATIONS_COLLECTION = "locations"
    }
}