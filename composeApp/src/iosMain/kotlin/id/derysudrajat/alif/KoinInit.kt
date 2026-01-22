package id.derysudrajat.alif

import id.derysudrajat.alif.di.AppModule.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}