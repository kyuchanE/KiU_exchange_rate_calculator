package team.devloopy.kiu_exchange_rate.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import team.devloopy.kiu_exchange_rate.base.BaseActivity
import team.devloopy.kiu_exchange_rate.base.BaseViewModel
import team.devloopy.kiu_exchange_rate.data.ExchangeRateRepository
import team.devloopy.kiu_exchange_rate.utils.*

class MainViewModel(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val activity: BaseActivity<*>
) : BaseViewModel() {

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
    fun getExchangeRateRepoData(){
        isLoading = true
        addDisposable(
            exchangeRateRepository.getExchangeRate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { t ->
                    L.d("getExchangeRateRepoData Error : ${t.message}")
                }
                .doOnNext { json ->
                    L.d("getExchangeRateRepoData Next : $json")
                    json?.let { item ->
                        try {
                            if (item.asBoolean("success")) {
                                timeLiveData.postValue(item.asLong("timestamp"))
                                with(item.asJsonObject("quotes")) {
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
                    isLoading = false
                }
                .subscribeWith(activity.buildSubscriber(isLoading))
        )
    }

    fun getTimestamp(timestamp: Long): String = timestamp.changeDate("yyyy-MM-dd hh:mm")
    fun getQuotesData(data: Double): String = data.count

}