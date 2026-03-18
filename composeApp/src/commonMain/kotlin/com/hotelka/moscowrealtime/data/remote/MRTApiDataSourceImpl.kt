package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.DiscoverDto
import com.hotelka.moscowrealtime.data.dto.api.ClosestLocationResultDto
import com.hotelka.moscowrealtime.data.dto.api.ErrorDataDto
import com.hotelka.moscowrealtime.data.dto.api.MRTApiDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MRTApiDataSourceImpl(
    private val client: HttpClient
) : MRTApiDataSource {
    override suspend fun analyzeImage(
        imgPath: String,
        userId: String
    ): MRTApiDto<DiscoverDto> = try {
        val response = client.submitFormWithBinaryData(
            url = "$BASE_URL/analyze",
            formData = formData {
                append("imgPath", imgPath)
                append("userId", userId)
            }
        )

        if (response.status.value != 200){
            val error = response.body<ErrorDataDto>()
            Logger.withTag("MRTApi").d { "Status != 200: ${error.error.toString()}"}
            throw Exception(error.error) as Throwable
        } else {
            MRTApiDto.Companion.success(response.body())
        }
    } catch (e: Exception){
        Logger.withTag("MRTApi").d { "Status != 200: ${e.message.toString()}"}
        MRTApiDto.Companion.failure("MrtApiResponse:Analyze: ${e.message}")
    }

    override suspend fun getClosestLocation(
        lat: Double,
        lon: Double
    ): MRTApiDto<ClosestLocationResultDto> = try {
        val result = client.get("$BASE_URL/closest"){
            parameter("lat", lat)
            parameter("lon", lon)
        }.body<ClosestLocationResultDto>()
        MRTApiDto.Companion.success(result)
    } catch (e: Exception){
        MRTApiDto.Companion.failure("MrtApiResponse:GetClosest: ${e.message}")
    }

    companion object{
        private const val BASE_URL = "https://moscow-realtime.sytes.net"
    }
}