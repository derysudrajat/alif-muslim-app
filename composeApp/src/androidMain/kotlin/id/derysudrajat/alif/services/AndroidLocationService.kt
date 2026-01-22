package id.derysudrajat.alif.services

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY

class AndroidLocationService(
    private val context: Context
) : LocationService {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location {
        return try {
            val location = fusedLocationClient.getCurrentLocation(
                PRIORITY_BALANCED_POWER_ACCURACY,
                null
            ).result

            location?.let {
                Location(it.latitude, it.longitude)
            } ?: throw LocationException("Location not found")
        } catch (e: Exception) {
            throw LocationException(e.message ?: "Unknown Android Location Error")
        }
    }
}