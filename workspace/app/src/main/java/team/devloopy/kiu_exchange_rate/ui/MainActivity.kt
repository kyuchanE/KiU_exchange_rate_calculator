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
import team.devloopy.kiu_exchange_rate.utils.count
import team.devloopy.kiu_exchange_rate.utils.dialog.ModalPickerDialog
import team.devloopy.kiu_exchange_rate.utils.toast
import java.nio.file.WatchEvent

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main
    lateinit var modalPickerDialog: ModalPickerDialog

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var currentCountry: C.CountryList.Current

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentCountry = C.CountryList.Current.K
        mainViewModel.setHandler(this)
        setDataObserve()
        initView()
    }

    private fun initView() {

        modalPickerDialog = ModalPickerDialog(this)

        with(binding.etRemittance){
            setText("0")
            addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
                override fun afterTextChanged(p0: Editable?) {
                    L.d("afterTextChanged ${binding.etRemittance.text}")
                }

            })
        }

        // getData
        mainViewModel.getExchangeRate()
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
            binding.search = str
        }
        mainViewModel.krwData.observe(this) {
            L.d("setDataObserve krw : $it ")
            setCountry(C.CountryList.Current.K, it)
        }
        mainViewModel.jpyData.observe(this) {
            L.d("setDataObserve jpy : $it ")
            setCountry(C.CountryList.Current.J, it)
        }
        mainViewModel.phpData.observe(this) {
            L.d("setDataObserve php : $it ")
            setCountry(C.CountryList.Current.P, it)
        }
    }

    private fun setCountry(country: C.CountryList.Current, changeRate: Double) {
        if (country == currentCountry) {
            var resultStr = ""
            val remittance: Int = if (binding.etRemittance.text.toString().isEmpty()) {
                0
            } else {
                binding.etRemittance.text.toString().toInt()
            }
            resultStr += (remittance * changeRate).count

            binding.country =  C.CountryList.getLists(
                this@MainActivity
            )[modalPickerDialog.getResultPosition()]

            when(currentCountry) {
                C.CountryList.Current.K -> {    // 한국
                    binding.exchange = "${changeRate.count} KRW / USD"
                    resultStr += " KRW"
                }
                C.CountryList.Current.J -> {    // 일본
                    binding.exchange = "${changeRate.count} JPY / USD"
                    resultStr += " JPY"
                }
                C.CountryList.Current.P -> {    // 필리핀
                    binding.exchange = "${changeRate.count} PHP / USD"
                    resultStr += " PHP"
                }
                else -> {}
            }

            binding.result = String.format(resources.getString(R.string.str_result), resultStr)
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
                currentCountry = when(modalPickerDialog.getResultPosition()){
                    0 -> C.CountryList.Current.K
                    1 -> C.CountryList.Current.J
                    2 -> C.CountryList.Current.P
                    else -> C.CountryList.Current.K
                }
                mainViewModel.getExchangeRate()
            }
            R.id.btn_calculate -> {     // 계산하기
                if (binding.etRemittance.text.toString().isEmpty()) {
                    "송금액을 입력하세요.".toast(this@MainActivity)
                    binding.etRemittance.setText("0")
                } else {
                    mainViewModel.getExchangeRate()
                }
            }
        }
    }
}