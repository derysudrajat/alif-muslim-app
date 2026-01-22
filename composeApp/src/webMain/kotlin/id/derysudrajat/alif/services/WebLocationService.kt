package id.derysudrajat.alif.services

import kotlinx.browser.window
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.JsAny
import kotlin.js.unsafeCast

external interface Geolocation : JsAny {
    fun getCurrentPosition(
        successCallback: (GeolocationPosition) -> Unit,
        errorCallback: (GeolocationPositionError) -> Unit
    )
}

external interface GeolocationPosition : JsAny {
    val coords: GeolocationCoordinates
}

external interface GeolocationCoordinates : JsAny {
    val latitude: Double
    val longitude: Double
}

external interface GeolocationPositionError : JsAny {
    val code: Int
    val message: String
}

// 2. The Custom Navigator Wrapper
external interface NavigatorWithGeo : JsAny {
    val geolocation: Geolocation
}

class WebLocationService : LocationService {

    override suspend fun getCurrentLocation(): Location = suspendCoroutine { cont ->
        // 3. Cast is now valid because NavigatorWithGeo extends JsAny
        val navigator = window.navigator.unsafeCast<NavigatorWithGeo>()

        navigator.geolocation.getCurrentPosition(
            successCallback = { position ->
                cont.resume(
                    Location(
                        latitude = position.coords.latitude,
                        longitude = position.coords.longitude
                    )
                )
            },
            errorCallback = { error ->
                cont.resumeWithException(LocationException("Web Location Error (${error.code}): ${error.message}"))
            }
        )
    }
}