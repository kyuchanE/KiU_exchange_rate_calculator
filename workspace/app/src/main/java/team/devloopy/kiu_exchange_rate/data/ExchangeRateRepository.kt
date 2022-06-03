package team.devloopy.kiu_exchange_rate.data

import com.google.gson.JsonObject
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import team.devloopy.kiu_exchange_rate.config.C
import team.devloopy.kiu_exchange_rate.model.BasicApi

class ExchangeRateRepository(private val api: BasicApi): KoinComponent{

    fun getExchangeRate(): Flowable<JsonObject> {
        return api.get(
            url = C.ApiLayer.url,
            params = mutableMapOf(
                "access_key" to "USD"
            ),
            headers = mutableMapOf(
                "apikey" to C.ApiLayer.key
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}