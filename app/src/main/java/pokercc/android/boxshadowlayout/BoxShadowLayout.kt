package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import kotlin.math.absoluteValue

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
    private var shadowBlur = 0f
    private var shadowInset = false
    private var shadowSpread = 0f
    private var radius = 0f
    private var topLeftRadius = 0f
    private var topRightRadius = 0f
    private var bottomLeftRadius = 0f
    private var bottomRightRadius = 0f
    private val clipPath = Path()
    private val clipPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val shadowPath = Path()
    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
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
            setShadowSpread(getDimension(R.styleable.BoxShadowLayout_shadowSpread, 0f))
            val radius = getDimension(R.styleable.BoxShadowLayout_radius, 0f)
            setRadius(radius)
            if (hasValue(R.styleable.BoxShadowLayout_topLeftRadius) ||
                hasValue(R.styleable.BoxShadowLayout_topRightRadius) ||
                hasValue(R.styleable.BoxShadowLayout_bottomLeftRadius) ||
                hasValue(R.styleable.BoxShadowLayout_bottomRightRadius)
            ) setRadius(
                getDimension(R.styleable.BoxShadowLayout_topLeftRadius, radius),
                getDimension(R.styleable.BoxShadowLayout_topRightRadius, radius),
                getDimension(R.styleable.BoxShadowLayout_bottomLeftRadius, radius),
                getDimension(R.styleable.BoxShadowLayout_bottomRightRadius, radius)
            )

        }.recycle()

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Tell parent don't clip me. Otherwise the shadow will be erase.
        (parent as? ViewGroup)?.clipChildren = false
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun draw(canvas: Canvas) {
        drawShadow(canvas)
        val saveCount = canvas.saveLayer(null, null)
        super.draw(canvas)
        clipRadius(canvas)
        canvas.restoreToCount(saveCount)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setRadius(
            this.topLeftRadius,
            this.topRightRadius,
            this.bottomLeftRadius,
            this.bottomRightRadius
        )
    }

    private fun clipRadius(canvas: Canvas) {
        if (radius > 0) {
            canvas.drawPath(clipPath, clipPaint)
        }
    }


    private fun drawShadow(canvas: Canvas) {
        canvas.drawPath(shadowPath, shadowPaint)
    }


    fun setShadowVerticalOffset(shadowVerticalOffset: Float) {
        this.shadowVerticalOffset = shadowVerticalOffset
        resetShadowOffset()
        invalidate()
    }

    fun getShadowVerticalOffset(): Float = this.shadowVerticalOffset

    fun setShadowHorizontalOffset(shadowHorizontalOffset: Float) {
        this.shadowHorizontalOffset = shadowHorizontalOffset
        resetShadowOffset()
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

    fun setShadowSpread(shadowSpread: Float) {
        this.shadowSpread = shadowSpread
        invalidate()
    }

    fun getShadowSpread() = shadowSpread

    fun setRadius(radius: Float) {
        this.radius = radius.absoluteValue
        setRadius(this.radius, this.radius, this.radius, this.radius)
    }


    fun getRadius(): Float = this.radius

    fun setRadius(topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
        this.topLeftRadius = topLeft.absoluteValue
        this.topRightRadius = topRight.absoluteValue
        this.bottomLeftRadius = bottomLeft.absoluteValue
        this.bottomRightRadius = bottomRight.absoluteValue
        clipPath.reset()
        clipPath.fillType = Path.FillType.INVERSE_WINDING
        clipPath.setRoundRect(
            this.topLeftRadius,
            this.topRightRadius,
            this.bottomLeftRadius,
            this.bottomRightRadius,
            width.toFloat(),
            height.toFloat()
        )
        shadowPath.reset()
        resetShadowOffset()
        shadowPath.fillType = Path.FillType.WINDING
        shadowPath.setRoundRect(
            this.topLeftRadius,
            this.topRightRadius,
            this.bottomLeftRadius,
            this.bottomRightRadius,
            width.toFloat() + shadowSpread * 2,
            height.toFloat() + shadowSpread * 2
        )
        invalidate()
    }

    private fun resetShadowOffset() {
        shadowPath.offset(
            -shadowSpread + shadowHorizontalOffset,
            -shadowSpread + shadowVerticalOffset
        )

    }

    fun getTopLeftRadius() = topLeftRadius
    fun getTopRightRadius() = topRightRadius
    fun getBottomRightRadius() = bottomRightRadius
    fun getBottomLeftRadius() = bottomLeftRadius


}

private fun Path.setRoundRect(
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float,
    width: Float,
    height: Float
) {
    // same radius
    if (topLeft == topRight && bottomLeft == bottomRight && topLeft == bottomLeft) {
        addRoundRect(
            0f,
            0f,
            width,
            height,
            topLeft,
            topLeft,
            Path.Direction.CW
        )
        return
    }
    // difference radius
    moveTo(0f, topLeft)
    if (topLeft > 0) {
        arcTo(
            0f,
            0f,
            topLeft * 2,
            topLeft * 2,
            180f,
            90f,
            false
        )
    }
    lineTo(width - topRight, 0f)
    if (topRight > 0) {
        arcTo(
            width - topRight * 2,
            0f,
            width,
            topRight * 2,
            270f,
            90f,
            false
        )
    }
    lineTo(width, height - bottomRight)
    if (bottomRight > 0) {
        arcTo(
            width - bottomRight * 2,
            height - bottomRight * 2,
            width,
            height,
            0f,
            90f,
            false
        )
    }
    lineTo(bottomLeft, height)
    if (bottomLeft > 0) {
        arcTo(
            0f,
            height - bottomLeft * 2,
            bottomLeft * 2,
            height,
            90f,
            90f,
            false
        )
    }
    close()
}