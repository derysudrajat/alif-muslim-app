package id.derysudrajat.alif.data.repository

import id.derysudrajat.alif.domain.abstraction.AppRepository
import id.derysudrajat.alif.domain.abstraction.PrayerRemoteDataSource
import id.derysudrajat.alif.domain.model.PrayerScheduleData

class AlifRepositoryImpl(
    private val remoteDataSource: PrayerRemoteDataSource
) : AppRepository {
    override suspend fun getSchedule(
        latitude: Double,
        longitude: Double,
        month: Int,
        year: Int
    ): List<PrayerScheduleData> = remoteDataSource.getSchedule(
        latitude, longitude, month, year
    )
}