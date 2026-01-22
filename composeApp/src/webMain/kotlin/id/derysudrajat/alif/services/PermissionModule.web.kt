package id.derysudrajat.alif.services

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val permissionModule: Module = module {
    singleOf(::WebPermissionControl) { bind<AppPermissionControl>() }
}