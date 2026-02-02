package id.derysudrajat.alif.services

import org.koin.core.module.Module
import org.koin.dsl.module

actual val permissionModule: Module = module {
    single<PermissionService> { WebPermissionService() }
}

class WebPermissionService : PermissionService {
    override suspend fun checkLocationPermission(): PermissionStatus {

        return PermissionStatus.GRANTED
    }

    override suspend fun requestLocationPermission() {
        println("Web: Browser handles permissions automatically")
    }

    override fun openSettings() {

    }
}