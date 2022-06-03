package team.devloopy.kiu_exchange_rate.di

import org.koin.dsl.module
import team.devloopy.kiu_exchange_rate.data.ExchangeRateRepository

val repositoryModule = module {
    factory {
        ExchangeRateRepository(get())
    }
}