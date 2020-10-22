package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt

/**
 * Box Shadow like css in web.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class BoxShadowLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    companion object {
        private const val LOG_TAG = "BoxShadowLayout"
        private const val DEBUG = false
    }

    private var shadowVerticalOffset = 0f
    private var shadowHorizontalOffset = 0f
    private var shadowColor = Color.RED
    private var shadowBlur = 5f * 3
    private var shadowInset = false
    private var radius = 0f

    init {
        setWillNotDraw(false)
        init(attrs, 0)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        context.obtainStyledAttributes(
            attrs, R.styleable.BoxShadowLayout, defStyle, 0
        ).apply {
            setShadowVerticalOffset(getFloat(R.styleable.BoxShadowLayout_shadowVerticalOffset, 0f))
            val hOffset = getFloat(R.styleable.BoxShadowLayout_shadowHorizontalOffset, 0f)
            setShadowHorizontalOffset(hOffset)
            setShadowColor(getColor(R.styleable.BoxShadowLayout_shadowColor, 0xff888888.toInt()))
            setShadowBlur(getFloat(R.styleable.BoxShadowLayout_shadowBlur, 0f))
            setShadowInset(getBoolean(R.styleable.BoxShadowLayout_shadowInset, false))
            setRadius(getFloat(R.styleable.BoxShadowLayout_radius, 0f))
        }.recycle()

    }


    private val shadowPaint = Paint().apply {
        color = shadowColor
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(shadowBlur, BlurMaskFilter.Blur.OUTER)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Tell parent don't clip me. Otherwise the shadow will be erase.
        (parent as? ViewGroup)?.clipChildren = false
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        require(childCount == 1) { "BoxShadowLayout only support one children!" }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawShadow(canvas)

    }

    private val target: View? get() = getChildAt(0)
    private fun drawShadow(canvas: Canvas) {
        target?.let {
            canvas.drawRoundRect(
                it.x + shadowHorizontalOffset,
                it.y + shadowVerticalOffset,
                it.x + it.width.toFloat() + shadowHorizontalOffset,
                it.y + it.height.toFloat() + shadowVerticalOffset,
                radius,
                radius,
                shadowPaint
            )

        }
    }


    fun setShadowVerticalOffset(shadowVerticalOffset: Float) {
        this.shadowVerticalOffset = shadowVerticalOffset
        invalidate()
    }

    fun getShadowVerticalOffset(): Float = this.shadowVerticalOffset

    fun setShadowHorizontalOffset(shadowHorizontalOffset: Float) {
        this.shadowHorizontalOffset = shadowHorizontalOffset
        invalidate()
    }

    fun getShadowHorizontalOffset(): Float = this.shadowHorizontalOffset

    fun setShadowColor(@ColorInt shadowColor: Int) {
        this.shadowColor = shadowColor
        shadowPaint.color = this.shadowColor
        invalidate()
    }

    fun getShadowColor(): Int = this.shadowColor

    fun setShadowBlur(shadowBlur: Float) {
        this.shadowBlur = shadowBlur
        resetBlur()
        invalidate()
    }

    fun setShadowInset(shadowInset: Boolean) {
        this.shadowInset = shadowInset
        resetBlur()
        invalidate()
    }

    private fun resetBlur() {
        val type = if (this.shadowInset) BlurMaskFilter.Blur.INNER else BlurMaskFilter.Blur.OUTER
        shadowPaint.maskFilter = BlurMaskFilter(this.shadowBlur, type)
    }

    fun isShadowInset(): Boolean = this.shadowInset

    fun setRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    fun getRadius(): Float = this.radius
}
