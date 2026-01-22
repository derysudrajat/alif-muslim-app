package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.ios.PermissionsController
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val permissionModule = module {
    single { PermissionsController() }
    singleOf(::MokoPermissionControl) { bind<AppPermissionControl>() }
}