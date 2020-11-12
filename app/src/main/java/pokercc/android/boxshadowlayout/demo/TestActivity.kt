package pokercc.android.boxshadowlayout.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.doOnTextChanged
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import pokercc.android.boxshadowlayout.demo.databinding.ActivityTestBinding


class TestActivity : AppCompatActivity() {
    companion object {
        fun start(activity: Activity, shareView: View, shareElementName: String) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                shareView,
                shareElementName
            )
            activity.startActivity(
                Intent(activity, TestActivity::class.java),
                activityOptions.toBundle()
            )
        }
    }

    private val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }

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

private fun EditText.setDpChange(onDpChange: (Px) -> Unit) {
    occupyParent()
    doOnTextChanged { text, _, _, _ ->
        val shadowBlur = (text?.toString()?.toIntOrNull() ?: 0).toFloat()
        onDpChange(Dp(shadowBlur).toPx())
    }
}

private fun View.occupyParent() {
    (parent as? View)?.setOnClickListener { this.performClick() }
}