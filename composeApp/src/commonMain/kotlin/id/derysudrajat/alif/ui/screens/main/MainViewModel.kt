package id.derysudrajat.alif.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.core.now
import dev.icerock.moko.permissions.PermissionsController
import id.derysudrajat.alif.domain.abstraction.AppRepository
import id.derysudrajat.alif.services.AppPermissionControl
import id.derysudrajat.alif.services.Location
import id.derysudrajat.alif.services.LocationService
import id.derysudrajat.alif.utils.ApiResult
import id.derysudrajat.alif.utils.UseCase
import kotlinx.coroutines.launch
import kotlinx.datetime.YearMonth

class MainViewModel(
    private val permissionControl: AppPermissionControl,
    private val locationService: LocationService,
    private val appRepository: AppRepository
) : ViewModel() {

    fun getPermissionsController(): PermissionsController = permissionControl.controller

    fun requestLocation(
        onSuccess: (location: Location) -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                permissionControl.checkAndRequestLocation()
                val location = locationService.getCurrentLocation()
                onSuccess(location)
                val yearMonth = YearMonth.now()
                println("Time: ${yearMonth.year}, ${yearMonth.month.ordinal + 1}")
                println("Location: $location")
                UseCase.execute(
                    {
                        appRepository.getSchedule(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            month = yearMonth.month.ordinal + 1,
                            year = yearMonth.year
                        )
                    }
                ) { result ->
                    when (result) {
                        ApiResult.Loading -> {
                            println("UseCase:Result:Loading")
                        }

                        is ApiResult.Success -> {
                            println("UseCase:Result:Success = ${result.data}")

                        }

                        is ApiResult.Failed -> {
                            println("UseCase:Result:Failed = ${result.exception.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                onFailed(e.message.orEmpty())
                println("Failed:requestLocation = ${e.message}")
            }
        }
    }
}