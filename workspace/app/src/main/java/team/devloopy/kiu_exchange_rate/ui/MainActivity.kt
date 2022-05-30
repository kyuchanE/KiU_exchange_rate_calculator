package team.devloopy.kiu_exchange_rate.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import team.devloopy.kiu_exchange_rate.R
import team.devloopy.kiu_exchange_rate.base.BaseActivity
import team.devloopy.kiu_exchange_rate.config.C
import team.devloopy.kiu_exchange_rate.databinding.ActivityMainBinding
import team.devloopy.kiu_exchange_rate.utils.L
import team.devloopy.kiu_exchange_rate.utils.changeDate
import team.devloopy.kiu_exchange_rate.utils.dialog.ModalPickerDialog
import java.nio.file.WatchEvent

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main
    lateinit var modalPickerDialog: ModalPickerDialog

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var currentCountry: C.CountryList.Current

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentCountry = C.CountryList.Current.K
        setDataObserve()
        initView()
    }

    private fun initView() {

        modalPickerDialog = ModalPickerDialog(this)

        binding.etRemittance.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                L.d("afterTextChanged ${binding.etRemittance.text}")
//                mainViewModel.getExchangeRate()
            }

        })

        // getData
//        mainViewModel.getExchangeRate()
    }

    // 화면 바깥에 클릭 시 키보드 숨김처리
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            binding.etRemittance.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setDataObserve() {
        mainViewModel.timeData.observe(this) {
            L.d("setDataObserve time : $it")
            val str = it.changeDate("yyyy-MM-dd hh:mm")
            L.d(" @@@@@@@@@@@@@@ $str")
        }
        mainViewModel.krwData.observe(this) {
            L.d("setDataObserve krw : $it ")
        }
        mainViewModel.jpyData.observe(this) {
            L.d("setDataObserve jpy : $it ")
        }
        mainViewModel.phpData.observe(this) {
            L.d("setDataObserve php : $it ")
        }
    }

    private fun setCountry(country: C.CountryList.Current, changeRate: Double, remittance: Int) {
        if (country == currentCountry) {
            when(currentCountry) {
                C.CountryList.Current.K -> {    // 한국

                }
                C.CountryList.Current.J -> {    // 일본

                }
                C.CountryList.Current.P -> {    // 필리핀

                }
                else -> {}
            }
        }

    }

    override fun onBtnEvents(v: View) {
        super.onBtnEvents(v)

        when(v.id) {
            R.id.ll_select_country -> {     // 수취 국가 선택
                modalPickerDialog.initPickerItems(
                    C.CountryList.getLists(this@MainActivity)
                )
            }
            R.id.btn_save_select -> {   // 수취 국가 선택 완료
                L.d("onBtnEvents : btn_save_select")
            }
        }
    }
}