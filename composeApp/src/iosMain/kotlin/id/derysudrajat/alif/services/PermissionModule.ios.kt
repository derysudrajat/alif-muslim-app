package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.PermissionsController
import org.koin.dsl.module
import dev.icerock.moko.permissions.ios.PermissionsController as IosPermissionsController

actual val permissionModule = module {
    single<PermissionsController> { IosPermissionsController() }

    single<AppPermissionControl> {
        MokoPermissionControl(
            controller = get()
        )
    }
}