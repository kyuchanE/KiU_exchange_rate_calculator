package team.devloopy.kiu_exchange_rate.utils.dialog

import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import team.devloopy.kiu_exchange_rate.R
import team.devloopy.kiu_exchange_rate.base.BaseActivity
import team.devloopy.kiu_exchange_rate.base.BaseDialog
import team.devloopy.kiu_exchange_rate.databinding.DialogModalPickerBinding
import team.devloopy.kiu_exchange_rate.utils.L

class ModalPickerDialog(activity: BaseActivity<*>) :
    BaseDialog<DialogModalPickerBinding>(activity, true) {

    override val layoutId: Int = R.layout.dialog_modal_picker

    init {
        // 생성/제거 애니메이션 추가
        window?.let {
            val params: WindowManager.LayoutParams = it.attributes
            params.windowAnimations = R.style.ModalDialogAnim
            it.attributes = params
        }
    }

    fun initPickerItems(itemList: MutableList<String>): ModalPickerDialog {
        showDialog()
        clickBottomBtn()
        L.d("initPickerItems : $itemList")

        binding.npPicker.apply {
            this.maxValue = itemList.size -1
            this.minValue = 0
            displayedValues = itemList.toTypedArray()
        }

        return this
    }

    private fun clickBottomBtn(){
        binding.btnSaveSelect.setOnClickListener {

            activity.onBtnEvents(it)
            hideDialog()
        }
        binding.clContainer.setOnClickListener {  }
    }

}