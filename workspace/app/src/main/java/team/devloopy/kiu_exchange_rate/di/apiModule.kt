package team.devloopy.kiu_exchange_rate.di

import org.koin.dsl.module
import team.devloopy.kiu_exchange_rate.model.BasicApi
import team.devloopy.kiu_exchange_rate.utils.Api

val apiModule = module {
    single {
        Api.build().create(BasicApi::class.java)
    }
}