package id.derysudrajat.alif

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.ios.PermissionsController
import id.derysudrajat.alif.services.PermissionService
import id.derysudrajat.alif.services.PermissionStatus
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class IosPermissionService(
    private val controller: PermissionsController
) : PermissionService {

    override suspend fun checkLocationPermission(): PermissionStatus {
        val statusLocation = controller.isPermissionGranted(Permission.LOCATION)
        val statusLocationCoarse = controller.isPermissionGranted(Permission.COARSE_LOCATION)
        return when {
            statusLocation || statusLocationCoarse -> PermissionStatus.GRANTED
            !statusLocation && !statusLocationCoarse -> PermissionStatus.DENIED
            else -> PermissionStatus.NOT_DETERMINED
        }
    }

    override suspend fun requestLocationPermission() {
        try {
            controller.providePermission(Permission.LOCATION)
        } catch (e: Exception) {
            println("Permission denied: ${e.message}")
        }
    }

    override fun openSettings() {
        val url = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
        if (url != null) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
}