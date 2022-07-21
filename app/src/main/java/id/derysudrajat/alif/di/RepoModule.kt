package id.derysudrajat.alif.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.repo.local.LocalDataSource
import id.derysudrajat.alif.repo.local.room.AlifDatabase
import id.derysudrajat.alif.repo.local.room.ReminderDao
import id.derysudrajat.alif.repo.remote.RemoteDataSource
import id.derysudrajat.alif.repo.remote.network.PrayerService
import id.derysudrajat.alif.repo.remote.network.QuranService
import id.derysudrajat.alif.service.PrayerAlarm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRetrofitClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun providePrayerService(client: OkHttpClient): PrayerService {
        val retrofit = Retrofit.Builder().baseUrl("https://api.aladhan.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        return retrofit.create(PrayerService::class.java)
    }

    @Provides
    fun provideQuranService(client: OkHttpClient): QuranService {
        val retrofit = Retrofit.Builder().baseUrl("https://api.npoint.io/99c279bb173a6e28359c/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        return retrofit.create(QuranService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(
        service: PrayerService,
        quranService: QuranService
    ): RemoteDataSource = RemoteDataSource(service, quranService)

    @Provides
    fun provideAlifDatabase(@ApplicationContext appContext: Context): AlifDatabase =
        AlifDatabase.getInstance(appContext)

    @Provides
    fun provideReminderDao(alifDatabase: AlifDatabase): ReminderDao = alifDatabase.reminderDao()

    @Provides
    fun provideLocalDataSource(reminderDao: ReminderDao) = LocalDataSource(reminderDao)

    @Provides
    fun providePrayerRepository(
        remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource
    ): PrayerRepository = PrayerRepository(remoteDataSource, localDataSource)

    @Provides
    fun providePrayerAlarm(): PrayerAlarm = PrayerAlarm()
}