package id.derysudrajat.alif.services

import org.koin.dsl.module

actual val platformModule = module {
    single<LocationService> { WebLocationService() }
}