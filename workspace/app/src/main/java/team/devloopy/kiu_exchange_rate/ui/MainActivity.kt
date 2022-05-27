package team.devloopy.kiu_exchange_rate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import team.devloopy.kiu_exchange_rate.R
import team.devloopy.kiu_exchange_rate.base.BaseActivity
import team.devloopy.kiu_exchange_rate.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {

    }
}