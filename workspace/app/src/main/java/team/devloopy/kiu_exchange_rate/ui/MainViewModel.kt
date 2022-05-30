package team.devloopy.kiu_exchange_rate.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import team.devloopy.kiu_exchange_rate.base.BaseViewModel
import team.devloopy.kiu_exchange_rate.config.C
import team.devloopy.kiu_exchange_rate.model.BasicApi
import team.devloopy.kiu_exchange_rate.utils.L

class MainViewModel(private val api: BasicApi): BaseViewModel() {
    private var timeLiveData = MutableLiveData<Long>()
    private var krwLiveData = MutableLiveData<Double>()
    private var jpyLiveData = MutableLiveData<Double>()
    private var phpLiveData = MutableLiveData<Double>()

    val timeData: LiveData<Long> get() = timeLiveData
    val krwData: LiveData<Double> get() = krwLiveData
    val jpyData: LiveData<Double> get() = jpyLiveData
    val phpData: LiveData<Double> get() = phpLiveData

    fun getExchangeRate() {
        addDisposable(
            api.get(
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
                .doOnError {
                    L.d("getExchangeRate Error : ${it.message}")
                }
                .doOnNext { json ->
                    L.d("getExchangeRate Next : $json")
                    json?.let {
                        try {
                            if (it.get("success").asBoolean) {
                                timeLiveData.postValue(it.get("timestamp").asLong)
                                with(it.get("quotes").asJsonObject) {
                                    krwLiveData.postValue(this.get("USDKRW").asDouble)
                                    jpyLiveData.postValue(this.get("USDJPY").asDouble)
                                    phpLiveData.postValue(this.get("USDPHP").asDouble)
                                }

                            }
                        } catch (e: Exception) {
                            L.d("getExchangeRate Next catch : ${e.message}")
                        }

                    }
                }
                .doFinally {
                    L.d("getExchangeRate Finally ")
                }
                .subscribe()
        )
    }
}