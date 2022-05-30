package team.devloopy.kiu_exchange_rate.config

import android.content.Context
import team.devloopy.kiu_exchange_rate.R

object C {

    object ApiLayer {
        val url: String = "http://www.apilayer.net/api/live"
        val key: String = "38C4bnbI9OysiWf0T3X6Ht83qDPvCbTE"
    }

    object CountryList {    // 수취 국가 리스트
        fun getLists(context: Context): MutableList<String> = mutableListOf(
            context.getString(R.string.country_krw),
            context.getString(R.string.country_jpy),
            context.getString(R.string.country_php)
        )
    }

}