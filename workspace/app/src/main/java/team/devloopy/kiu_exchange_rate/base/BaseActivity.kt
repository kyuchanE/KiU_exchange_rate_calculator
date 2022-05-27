package team.devloopy.kiu_exchange_rate.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import team.devloopy.kiu_exchange_rate.R
import team.devloopy.kiu_exchange_rate.databinding.LoadingBinding
import team.devloopy.kiu_exchange_rate.utils.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {
    companion object{
        // 이벤트 주기
        private const val THROTTLE_FIRST_DURATION = 500L
    }

    // view data binding
    protected lateinit var binding: B
        private set

    // data binding layoutId
    abstract val layoutId: Int

    // Rx handler
    private val compositeDisposable = CompositeDisposable()

    // Rx lifecycle
    val rxLifeCycle = BehaviorSubject.create<ActivityEvent>()

    // Rx event
    private val btnEventsSubject = PublishSubject.create<View>()     // 버튼 이벤트

    // applicationContext
    val context: Context get() = applicationContext

    // 기본 에러 핸들러 on/off
    var useHandleError = true

    // loading
    private lateinit var loadingBinding: LoadingBinding
    // 로딩 뷰 사용 카운트
    private val loadingCount = AtomicInteger()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bind(layoutId)
        binding.setOnEvents()

        loadingBinding = bindView(R.layout.loading)
        (binding.root as ViewGroup).addView(
            loadingBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        rxLifeCycle.onNext(ActivityEvent.CREATE)

        btnEventsSubject
            .default()
            .doOnNext(::onBtnEvents)
            .subscribe()
    }

    override fun onResume() {
        super.onResume()
        rxLifeCycle.onNext(ActivityEvent.RESUME)
    }

    override fun onStart() {
        super.onStart()
        rxLifeCycle.onNext(ActivityEvent.START)
    }

    override fun onStop() {
        super.onStop()
        rxLifeCycle.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        // rx clear : 등록된 모든 핸들 중지
        compositeDisposable.clear()

        super.onDestroy()
        rxLifeCycle.onNext(ActivityEvent.DESTROY)
    }

    /**
     * 로딩표시
     */
    fun showLoading() {
        if (loadingCount.incrementAndGet() == 1) {
            runOnUiThread {
                loadingBinding.root.show()
            }
        }
    }

    /**
     * 로딩숨김
     */
    fun hideLoading() {
        if (loadingCount.decrementAndGet() == 0) {
            runOnUiThread {
                loadingBinding.root.gone()
            }
        }
    }

    /**
     * Rx 핸들을 핸들러에 등록
     *
     * @param disposable Rx 핸들
     */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * Rx 핸들을 핸들러에 제외
     *
     * @param disposable Rx 핸들
     */
    fun deleteDisposable(disposable: Disposable) {
        compositeDisposable.delete(disposable)
    }

    /**
     * Rx 핸들을 중지하고 핸들러에서 제외
     *
     * @param disposable Rx 핸들
     */
    fun removeDisposable(disposable: Disposable) {
        compositeDisposable.remove(disposable)
    }

    fun <T> PublishSubject<T>.default(): Observable<T> {
        return this.observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(THROTTLE_FIRST_DURATION, TimeUnit.MILLISECONDS)
            .doOnSubscribe(::addDisposable)
    }

    /**
     * 버튼 이벤트 처리
     *
     * @param v
     */
    open fun onBtnEvents(v: View) {}

    /**
     * 버튼 이벤트 처리 RX
     *
     * @param v
     */
    fun onRxBtnEvents(v: View) {
        btnEventsSubject.onNext(v)
    }
}