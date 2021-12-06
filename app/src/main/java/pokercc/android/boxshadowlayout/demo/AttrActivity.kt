package pokercc.android.boxshadowlayout.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.doOnTextChanged
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import pokercc.android.boxshadowlayout.demo.databinding.AttrActivitiyBinding


class AttrActivity : AppCompatActivity() {
    companion object {
        fun start(activity: Activity, shareView: View, shareElementName: String) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                shareView,
                shareElementName
            )
            activity.startActivity(
                Intent(activity, AttrActivity::class.java),
                activityOptions.toBundle()
            )
        }
    }

    private val binding by lazy { AttrActivitiyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickEvents()

    }

    private fun setClickEvents() {
        setUpColorPicker()
        val boxShadow = binding.boxShadowLayout
        binding.shadowBlur.setDpChange {
            boxShadow.setShadowBlur(it.toFloat())
        }
        binding.shadowHorizontalOffset.setDpChange {
            boxShadow.setShadowHorizontalOffset(it.toFloat())
        }
        binding.shadowVerticalOffset.setDpChange {
            boxShadow.setShadowVerticalOffset(it.toFloat())
        }
        binding.shadowSpread.setDpChange {
            boxShadow.setShadowSpread(it.toFloat())
        }
        binding.shadowInset.occupyParent()
        binding.shadowInset.setOnClickListener {
            binding.shadowInset.isSelected = !binding.shadowInset.isSelected
            binding.shadowInset.text = binding.shadowInset.isSelected.toString()
            boxShadow.setShadowInset(binding.shadowInset.isSelected)
        }
        binding.boxRadiusTopLeft.setDpChange {
            boxShadow.setBoxRadius(
                it.toFloat(),
                boxShadow.getTopRightRadius(),
                boxShadow.getBottomLeftRadius(),
                boxShadow.getBottomRightRadius()
            )
        }
        binding.boxRadiusTopRight.setDpChange {
            boxShadow.setBoxRadius(
                boxShadow.getTopLeftRadius(),
                it.toFloat(),
                boxShadow.getBottomLeftRadius(),
                boxShadow.getBottomRightRadius()
            )
        }
        binding.boxRadiusBottomLeft.setDpChange {
            boxShadow.setBoxRadius(
                boxShadow.getTopLeftRadius(),
                boxShadow.getTopRightRadius(),
                it.toFloat(),
                boxShadow.getBottomRightRadius()
            )
        }
        binding.boxRadiusBottomRight.setDpChange {
            boxShadow.setBoxRadius(
                boxShadow.getTopLeftRadius(),
                boxShadow.getTopRightRadius(),
                boxShadow.getBottomLeftRadius(),
                it.toFloat()
            )
        }
    }


    private fun setUpColorPicker() {
        val defaultColorStr = "#ff000000"
        binding.shadowColor.text = defaultColorStr
        binding.shadowColor.setBackgroundColor(Color.parseColor(defaultColorStr))
        binding.shadowColor.occupyParent()
        binding.shadowColor.setOnClickListener {
            var colorPicker: Dialog? = null
            colorPicker = ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose Blur Color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .noSliders()
                .setOnColorSelectedListener { selectedColor ->
                    @SuppressLint("SetTextI18n")
                    binding.shadowColor.text = "#${Integer.toHexString(selectedColor)}"
                    binding.shadowColor.setBackgroundColor(selectedColor)
                    binding.boxShadowLayout.setShadowColor(selectedColor)
                    colorPicker?.dismiss()
                }
                .build()
            colorPicker?.show()
        }
    }


}

private fun TextView.setDpChange(onDpChange: (Px) -> Unit) {
    occupyParent()
    setOnClickListener { view ->
        NumberPickerDialog(view.context) {
            onDpChange(Px(it))
            this.text = it.toString()
        }.show()
    }
}

private fun View.occupyParent() {
    (parent as? View)?.setOnClickListener { this.performClick() }
}

private class NumberPickerDialog(context: Context, private val onSelectedNumber: (Number) -> Unit) :
    AppCompatDialog(context) {
    private val numberPicker = NumberPicker(context)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 30
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            onSelectedNumber(newVal)
        }
    }

    override fun onStart() {
        super.onStart()
        setTitle(null)
        window?.attributes = window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
        }
        window?.setGravity(Gravity.BOTTOM)
        window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

}