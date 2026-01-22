package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val permissionModule = module {
    single { PermissionsController(applicationContext = androidContext()) }
    singleOf(::MokoPermissionControl) { bind<AppPermissionControl>() }
}