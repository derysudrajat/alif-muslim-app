package id.derysudrajat.alif.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.core.now
import id.derysudrajat.alif.domain.abstraction.AppRepository
import id.derysudrajat.alif.domain.model.PrayerScheduleData
import id.derysudrajat.alif.services.Location
import id.derysudrajat.alif.services.LocationService
import id.derysudrajat.alif.services.PermissionService
import id.derysudrajat.alif.utils.ApiResult
import id.derysudrajat.alif.utils.UseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

class MainViewModel(
    private val permissionService: PermissionService,
    private val locationService: LocationService,
    private val appRepository: AppRepository
) : ViewModel() {

    fun requestLocation(
        onSuccess: (location: Location) -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                supervisorScope {
                    launch { permissionService.checkLocationPermission() }
                    launch {
                        val location = locationService.getCurrentLocation()
                        onSuccess(location)
                    }
                }
            } catch (e: Exception) {
                onFailed(e.message.orEmpty())
                println("Failed:requestLocation = ${e.message}")
            }
        }
    }

    fun getSchedules(
        location: Location,
        onSuccess: (todaySchedule: PrayerScheduleData, listMonthSchedule: List<PrayerScheduleData>) -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        viewModelScope.launch {
            val yearMonth = YearMonth.now()
            val now = LocalDate.now()
            println("Time: ${yearMonth.year}, ${yearMonth.month.ordinal + 1}")

            UseCase.execute({
                appRepository.getSchedule(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    month = yearMonth.month.ordinal + 1,
                    year = yearMonth.year
                )
            }) { result ->
                when (result) {
                    is ApiResult.Failed -> {
                        println("Failed: ${result.exception}")
                        onFailed(result.exception.message.orEmpty())
                    }

                    ApiResult.Loading -> {}
                    is ApiResult.Success -> {
                        println("Success: ${result.data}")
                        result.data.find { d -> d.georgianDate.day == now.day }.let { data ->
                            println("Today: $data")
                            onSuccess(data ?: PrayerScheduleData.Empty, result.data)
                        }
                    }
                }
            }
        }
    }

    fun getSchedulesInMonth(
        location: Location,
        month: Int,
        year: Int,
        onSuccess: (listMonthSchedule: List<PrayerScheduleData>) -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        viewModelScope.launch {

            UseCase.execute({
                appRepository.getSchedule(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    month = month,
                    year = year
                )
            }) { result ->
                when (result) {
                    is ApiResult.Failed -> {
                        println("Failed: ${result.exception}")
                        onFailed(result.exception.message.orEmpty())
                    }

                    ApiResult.Loading -> {}
                    is ApiResult.Success -> {
                        println("Success: ${result.data}")
                        onSuccess(result.data)
                    }
                }
            }
        }
    }
}