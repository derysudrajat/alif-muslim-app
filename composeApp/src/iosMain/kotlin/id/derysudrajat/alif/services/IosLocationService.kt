package id.derysudrajat.alif.services

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class IosLocationService : LocationService {
    private val locationManager = CLLocationManager()

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getCurrentLocation(): Location = suspendCoroutine { cont ->
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest

        // Create a delegate to listen for the async result
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val locations = didUpdateLocations as List<CLLocation>
                val validLocation = locations.lastOrNull()
                println("listLocation = $locations")
                println("validLocation = $validLocation")
                if (validLocation != null) {
                    locationManager.stopUpdatingLocation()
                    cont.resume(
                        Location(
                        validLocation.coordinate.useContents { latitude },
                        validLocation.coordinate.useContents { longitude }
                    ))
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                cont.resumeWithException(LocationException(didFailWithError.localizedDescription))
            }
        }

        locationManager.delegate = delegate
        locationManager.startUpdatingLocation()
    }
}