package id.derysudrajat.alif.domain.abstraction

import id.derysudrajat.alif.domain.model.PrayerScheduleData

interface PrayerRemoteDataSource {
    suspend fun getSchedule(
        latitude: Double,
        longitude: Double,
        month: Int,
        year: Int
    ): List<PrayerScheduleData>
}