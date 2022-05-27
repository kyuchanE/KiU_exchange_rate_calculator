package team.devloopy.kiu_exchange_rate.base

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import team.devloopy.kiu_exchange_rate.di.activityModule

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // debug
        Stetho.initializeWithDefaults(this)

        // Koin
        startKoin {
            androidContext(this@BaseApplication)
            module {
                activityModule
            }
        }
    }

}