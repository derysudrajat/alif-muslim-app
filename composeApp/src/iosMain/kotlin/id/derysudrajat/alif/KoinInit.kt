package id.derysudrajat.alif

import id.derysudrajat.alif.di.AppModule.appModule
import id.derysudrajat.alif.services.permissionModule
import id.derysudrajat.alif.services.platformModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            appModule,
            permissionModule,
            platformModule
        )
    }
}