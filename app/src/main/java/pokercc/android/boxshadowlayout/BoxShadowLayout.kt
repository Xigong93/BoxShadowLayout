package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.*
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
    private val radiusPath = Path()
    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
    }
    private val clipPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }


    init {
        setWillNotDraw(false)
        init(attrs, 0)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        context.obtainStyledAttributes(
            attrs, R.styleable.BoxShadowLayout, defStyle, 0
        ).apply {
            val vOffset = getDimension(R.styleable.BoxShadowLayout_shadowVerticalOffset, 0f)
            setShadowVerticalOffset(vOffset)
            val hOffset = getDimension(R.styleable.BoxShadowLayout_shadowHorizontalOffset, 0f)
            setShadowHorizontalOffset(hOffset)
            setShadowColor(getColor(R.styleable.BoxShadowLayout_shadowColor, 0xff888888.toInt()))
            setShadowBlur(getDimension(R.styleable.BoxShadowLayout_shadowBlur, 0f))
            setShadowInset(getBoolean(R.styleable.BoxShadowLayout_shadowInset, false))
            setRadius(getDimension(R.styleable.BoxShadowLayout_radius, 0f))
        }.recycle()

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


    override fun draw(canvas: Canvas) {
        drawShadow(canvas)
        val saveCount = canvas.saveLayer(null, null)
        super.draw(canvas)
        clipRadius(canvas)
        canvas.restoreToCount(saveCount)

    }


    private fun clipRadius(canvas: Canvas) {
        if (radius > 0) {
            canvas.drawPath(radiusPath, clipPaint)
        }
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
        shadowPaint.maskFilter =
            if (this.shadowBlur == 0f) null else BlurMaskFilter(this.shadowBlur, type)
    }

    fun isShadowInset(): Boolean = this.shadowInset

    fun setRadius(radius: Float) {
        this.radius = radius
        radiusPath.reset()
        radiusPath.addRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            this.radius,
            this.radius,
            Path.Direction.CW
        )
        invalidate()
    }

    fun getRadius(): Float = this.radius

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radiusPath.reset()
        radiusPath.fillType = Path.FillType.INVERSE_WINDING
        radiusPath.addRoundRect(
            RectF(0f, 0f, w.toFloat(), h.toFloat()),
            this.radius,
            this.radius,
            Path.Direction.CW
        )
    }
}
