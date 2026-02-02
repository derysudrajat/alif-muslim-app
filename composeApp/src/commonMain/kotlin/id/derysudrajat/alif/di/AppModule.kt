package id.derysudrajat.alif.di

import id.derysudrajat.alif.data.local.QuranLocalDataSource
import id.derysudrajat.alif.data.local.QuranStore
import id.derysudrajat.alif.data.remote.api.PrayerApi
import id.derysudrajat.alif.data.remote.api.QuranApi
import id.derysudrajat.alif.data.remote.datasource.PrayerRemoteDataSourceImpl
import id.derysudrajat.alif.data.repository.AlifRepositoryImpl
import id.derysudrajat.alif.domain.abstraction.AppRepository
import id.derysudrajat.alif.domain.abstraction.PrayerRemoteDataSource
import id.derysudrajat.alif.services.permissionModule
import id.derysudrajat.alif.services.platformModule
import id.derysudrajat.alif.ui.screens.main.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val appModule = module {
        single {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger = Logger.SIMPLE
                }
            }
        }
        includes(permissionModule)
        includes(platformModule)

        singleOf(::PrayerApi)
        singleOf(::QuranApi)
        singleOf(::QuranStore)
        single {
            QuranLocalDataSource(get())
        }
        single<PrayerRemoteDataSource> {
            PrayerRemoteDataSourceImpl(get())
        }
        single<AppRepository> {
            AlifRepositoryImpl(get())
        }
        viewModel {
            MainViewModel(get(), get(), get())
        }
    }
}