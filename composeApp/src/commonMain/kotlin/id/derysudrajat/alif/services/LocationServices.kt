package id.derysudrajat.alif.services

import org.koin.core.module.Module

data class Location(val latitude: Double, val longitude: Double)

interface LocationService {
    suspend fun getCurrentLocation(): Location
}

class LocationException(message: String) : Exception(message)

expect val platformModule: Module

