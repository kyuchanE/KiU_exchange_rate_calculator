package team.devloopy.kiu_exchange_rate.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.handleCoroutineException
import team.devloopy.kiu_exchange_rate.base.BaseViewModel
import team.devloopy.kiu_exchange_rate.config.C
import team.devloopy.kiu_exchange_rate.model.BasicApi
import team.devloopy.kiu_exchange_rate.utils.*

class MainViewModel(private val api: BasicApi): BaseViewModel() {

    private var timeLiveData = MutableLiveData<Long>()
    private var krwLiveData = MutableLiveData<Double>()
    private var jpyLiveData = MutableLiveData<Double>()
    private var phpLiveData = MutableLiveData<Double>()

    val timeData: LiveData<Long> get() = timeLiveData
    val krwData: LiveData<Double> get() = krwLiveData
    val jpyData: LiveData<Double> get() = jpyLiveData
    val phpData: LiveData<Double> get() = phpLiveData

    private var isLoading: Boolean = false

    @SuppressLint("CheckResult")
    fun getExchangeRate() {
        isLoading = true
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
                        if (it.asBoolean("success")) {
                            timeLiveData.postValue(it.asLong("timestamp"))
                            with(it.asJsonObject("quotes")) {
                                krwLiveData.postValue(this.asDouble("USDKRW"))
                                jpyLiveData.postValue(this.asDouble("USDJPY"))
                                phpLiveData.postValue(this.asDouble("USDPHP"))
                            }
                        }
                    } catch (e: Exception) {
                        L.d("getExchangeRate Next catch : ${e.message}")
                    }

                }
            }
            .doFinally {
                L.d("getExchangeRate Finally ")
                isLoading = false
            }
            .subscribeWith(
                getHandler()?.buildSubscriber(isLoading)
            )
    }
}