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
    private val locationManager by lazy { CLLocationManager() }

    // 1. Keep a strong reference to the delegate here!
    // If it's a local variable inside the function, it gets garbage collected.
    private var locationDelegate: LocationDelegate? = null

    override suspend fun getCurrentLocation(): Location = suspendCoroutine { continuation ->
        // 2. Configure Manager
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest

        // 3. Create and assign the delegate
        val delegate = LocationDelegate(continuation) {
            // Cleanup callback when done
            locationManager.stopUpdatingLocation()
            locationDelegate = null
        }

        // Save it to the class property so it doesn't vanish
        locationDelegate = delegate
        locationManager.delegate = delegate

        // 4. Start
        locationManager.startUpdatingLocation()
    }

    // 5. Define the Delegate as a proper private inner class
    private class LocationDelegate(
        private val continuation: kotlin.coroutines.Continuation<Location>,
        private val onComplete: () -> Unit
    ) : NSObject(), CLLocationManagerDelegateProtocol {

        // Flag to prevent double-resuming (The fix for "Already Resumed"!)
        private var isResumed = false

        @OptIn(ExperimentalForeignApi::class)
        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            val locations = didUpdateLocations as List<CLLocation>
            val validLocation = locations.lastOrNull()

            if (validLocation != null && !isResumed) {
                isResumed = true
                onComplete() // Clean up the parent reference

                val lat = validLocation.coordinate.useContents { latitude }
                val long = validLocation.coordinate.useContents { longitude }

                continuation.resume(Location(lat, long))
            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            if (!isResumed) {
                isResumed = true
                onComplete()
                continuation.resumeWithException(Exception(didFailWithError.localizedDescription))
            }
        }
    }
}