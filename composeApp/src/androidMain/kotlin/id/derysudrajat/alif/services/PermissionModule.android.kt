package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.PermissionsController
import id.derysudrajat.alif.AndroidPermissionService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val permissionModule = module {
    single { PermissionsController(applicationContext = androidContext()) }
    single<PermissionService> { AndroidPermissionService(get(), get()) }
}