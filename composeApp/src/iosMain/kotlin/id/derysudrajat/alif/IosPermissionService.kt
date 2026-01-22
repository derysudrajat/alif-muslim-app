package id.derysudrajat.alif

import id.derysudrajat.alif.services.PermissionService
import id.derysudrajat.alif.services.PermissionStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

class IosPermissionService : PermissionService {
    private val locationManager = CLLocationManager()

    override suspend fun checkLocationPermission(): PermissionStatus {
        val status = locationManager.authorizationStatus
        return when (status) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> PermissionStatus.GRANTED

            kCLAuthorizationStatusDenied -> PermissionStatus.DENIED
            else -> PermissionStatus.NOT_DETERMINED
        }
    }

    override suspend fun requestLocationPermission() {
        locationManager.requestWhenInUseAuthorization()
    }

    override fun openSettings() {
        val url = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
        if (url != null) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
}