package team.devloopy.kiu_exchange_rate.listener

import io.reactivex.subscribers.ResourceSubscriber

open class RequestSubscriber<T> : ResourceSubscriber<T>(){
    var skipErrorHandle = false
    override fun onNext(t: T) {}
    override fun onError(t: Throwable) {}
    override fun onComplete() {}
}