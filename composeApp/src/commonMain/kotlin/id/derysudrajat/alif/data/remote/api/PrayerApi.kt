package id.derysudrajat.alif.data.remote.api

import id.derysudrajat.alif.data.remote.response.PrayerScheduleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class PrayerApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.aladhan.com/v1"
    }

    suspend fun getSchedule(
        latitude: Double,
        longitude: Double,
        month: Int,
        year: Int,
        method: Int = 2
    ): PrayerScheduleResponse {
        return client.get("${BASE_URL}/calendar") {
            url {
                parameter("latitude", latitude)
                parameter("longitude", longitude)
                parameter("month", month)
                parameter("year", year)
                parameter("method", method)
            }
        }.body()
    }

}