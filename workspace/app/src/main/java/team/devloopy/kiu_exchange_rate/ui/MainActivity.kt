package team.devloopy.kiu_exchange_rate.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import team.devloopy.kiu_exchange_rate.R
import team.devloopy.kiu_exchange_rate.base.BaseActivity
import team.devloopy.kiu_exchange_rate.config.C
import team.devloopy.kiu_exchange_rate.databinding.ActivityMainBinding
import team.devloopy.kiu_exchange_rate.utils.L
import team.devloopy.kiu_exchange_rate.utils.dialog.ModalPickerDialog
import java.nio.file.WatchEvent

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = R.layout.activity_main
    lateinit var modalPickerDialog: ModalPickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {

        modalPickerDialog = ModalPickerDialog(this)

        binding.etRemittance.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                L.d("afterTextChanged ${binding.etRemittance.text}")
            }

        })
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