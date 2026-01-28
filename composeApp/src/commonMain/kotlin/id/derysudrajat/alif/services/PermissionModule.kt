package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.PermissionsController
import org.koin.core.module.Module

expect val permissionModule: Module

interface AppPermissionControl {
    val controller: PermissionsController
    suspend fun checkAndRequestLocation()
    fun openSettings()
}

enum class PermissionStatus {
    GRANTED, DENIED, NOT_DETERMINED
}

interface PermissionService {
    suspend fun checkLocationPermission(): PermissionStatus
    suspend fun requestLocationPermission()
    fun openSettings() // Helper to send user to settings if permanently denied
}