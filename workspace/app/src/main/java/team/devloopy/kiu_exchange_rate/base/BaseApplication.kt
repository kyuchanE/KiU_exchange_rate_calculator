package team.devloopy.kiu_exchange_rate.base

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import team.devloopy.kiu_exchange_rate.di.activityModule
import team.devloopy.kiu_exchange_rate.di.apiModule
import team.devloopy.kiu_exchange_rate.di.repositoryModule
import team.devloopy.kiu_exchange_rate.utils.Api

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // debug
        Stetho.initializeWithDefaults(this)

        // 통신모듈
        Api.init(this)

        // Koin
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                activityModule,
                apiModule,
                repositoryModule
            )
        }
    }

}