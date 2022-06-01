package team.devloopy.kiu_exchange_rate.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var handler: BaseActivity<*>? = null

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun setHandler(handler: BaseActivity<*>){
        this.handler = handler
    }

    fun getHandler(): BaseActivity<*>? = handler
}