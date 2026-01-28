package id.derysudrajat.alif.data.remote.datasource

import id.derysudrajat.alif.data.remote.api.PrayerApi
import id.derysudrajat.alif.domain.abstraction.PrayerRemoteDataSource
import id.derysudrajat.alif.domain.model.PrayerScheduleData
import id.derysudrajat.alif.domain.model.toSchedule

class PrayerRemoteDataSourceImpl(
    private val prayerApi: PrayerApi
) : PrayerRemoteDataSource {

    override suspend fun getSchedule(
        latitude: Double,
        longitude: Double,
        month: Int,
        year: Int
    ): List<PrayerScheduleData> {
        return prayerApi.getSchedule(latitude, longitude, month, year).data?.toSchedule()
            ?: listOf()
    }
}