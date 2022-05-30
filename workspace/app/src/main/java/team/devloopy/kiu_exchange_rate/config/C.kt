package team.devloopy.kiu_exchange_rate.config

import android.content.Context
import team.devloopy.kiu_exchange_rate.R

object C {

    object ApiLayer {
        val url: String = "live"
        val baseUrl: String = "https://api.apilayer.com/currency_data/"
        val key: String = "DqTUq2KR011gmGxtxhAEZXiGaZMie1BF"
    }

    object CountryList {    // 수취 국가 리스트
        enum class Current {
            K, J, P
        }

        fun getLists(context: Context): MutableList<String> = mutableListOf(
            context.getString(R.string.country_krw),
            context.getString(R.string.country_jpy),
            context.getString(R.string.country_php)
        )

    }

}