package id.derysudrajat.alif

import android.app.Application
import id.derysudrajat.alif.di.AppModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AlifApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlifApp)
            modules(appModule)
        }
    }
}