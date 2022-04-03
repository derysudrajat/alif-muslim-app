package id.derysudrajat.alif.repo.remote.network

import id.derysudrajat.alif.repo.remote.response.PrayerScheduleResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerService {
    //latitude=-6.650216&longitude=108.538614&method=2&month=3&year=2022
    @GET("calendar")
    suspend fun getSchedule(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        @Query("month") month: Int,
        @Query("year") year: Int,
        @Query("method") method: Int = 2
    ): PrayerScheduleResponse
}