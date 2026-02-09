package id.derysudrajat.alif.services

import id.derysudrajat.alif.IosPermissionService
import org.koin.dsl.module
import dev.icerock.moko.permissions.ios.PermissionsController as IosPermissionsController

actual val permissionModule = module {
    single { IosPermissionsController() }

    single<PermissionService> {
        IosPermissionService(controller = get())
    }
}