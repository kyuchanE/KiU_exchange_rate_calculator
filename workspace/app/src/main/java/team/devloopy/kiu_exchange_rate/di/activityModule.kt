package team.devloopy.kiu_exchange_rate.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import team.devloopy.kiu_exchange_rate.base.BaseActivity
import team.devloopy.kiu_exchange_rate.ui.MainActivity
import team.devloopy.kiu_exchange_rate.ui.MainViewModel

val activityModule = module {
    viewModel { (activity: BaseActivity<*>) ->
        MainViewModel(get(), activity)
    }

}