package id.derysudrajat.alif.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.derysudrajat.alif.services.AppPermissionControl
import id.derysudrajat.alif.services.LocationService
import kotlinx.coroutines.launch

class MainViewModel(
    private val permissionControl: AppPermissionControl,
    private val locationService: LocationService,
) : ViewModel() {

    fun requestLocation() {
        viewModelScope.launch {
            try {
                permissionControl.checkAndRequestLocation()

                val location = locationService.getCurrentLocation()
                println("Location: $location")
            } catch (e: Exception) {
                println("Failed:requestLocation = ${e.message}")
            }
        }
    }
}