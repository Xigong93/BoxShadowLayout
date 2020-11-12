package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Trace
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Px
import kotlin.math.absoluteValue

/**
 * Box Shadow like css in web.
 * @author pokercc
 * @date 2020-10-20 00:10:19
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class BoxShadowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val LOG_TAG = "BoxShadowLayout"
        private const val DEBUG = false
    }

    private val clipPath = Path()
    private var shadowColor = Color.RED
    private var shadowBlur = 0f
    private var shadowInset = false
    private var shadowSpread = 0f
    private var boxRadius = 0f
    private var topLeftBoxRadius = 0f
    private var topRightBoxRadius = 0f
    private var bottomLeftBoxRadius = 0f
    private var bottomRightBoxRadius = 0f
    private var shadowVerticalOffset = 0f
    private var shadowHorizontalOffset = 0f
    private val clipPaint = Paint().apply {
        isDither = true
        isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val shadowPath = Path()

    private val shadowDrawable: ShadowDrawable =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BlurMaskShadowDrawable(shadowPath)
        } else {
            BlurMaskBitmapShadowDrawable(shadowPath)
        }


    init {
        setWillNotDraw(false)
        init(attrs, defStyleAttr)
    }


    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        // Load attributes
        context.obtainStyledAttributes(attrs, R.styleable.BoxShadowLayout, defStyleAttr, 0).apply {
            val vOffset = getDimension(R.styleable.BoxShadowLayout_shadowOffsetVertical, 0f)
            setShadowVerticalOffset(vOffset)
            val hOffset = getDimension(R.styleable.BoxShadowLayout_shadowOffsetHorizontal, 0f)
            setShadowHorizontalOffset(hOffset)
            setShadowColor(getColor(R.styleable.BoxShadowLayout_shadowColor, 0xff888888.toInt()))
            setShadowBlur(getDimension(R.styleable.BoxShadowLayout_shadowBlur, 0f))
            setShadowInset(getBoolean(R.styleable.BoxShadowLayout_shadowInset, false))
            setShadowSpread(getDimension(R.styleable.BoxShadowLayout_shadowSpread, 0f))
            boxRadius = getDimension(R.styleable.BoxShadowLayout_boxRadius, 0f)
            if (hasValue(R.styleable.BoxShadowLayout_boxRadiusTopLeft) ||
                hasValue(R.styleable.BoxShadowLayout_boxRadiusTopRight) ||
                hasValue(R.styleable.BoxShadowLayout_boxRadiusBottomLeft) ||
                hasValue(R.styleable.BoxShadowLayout_boxRadiusBottomRight)
            ) {
                setBoxRadius(
                    getDimension(R.styleable.BoxShadowLayout_boxRadiusTopLeft, boxRadius),
                    getDimension(R.styleable.BoxShadowLayout_boxRadiusTopRight, boxRadius),
                    getDimension(R.styleable.BoxShadowLayout_boxRadiusBottomLeft, boxRadius),
                    getDimension(R.styleable.BoxShadowLayout_boxRadiusBottomRight, boxRadius)
                )
            } else {
                setBoxRadius(boxRadius)
            }


        }.recycle()

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Tell parent don't clip me. Otherwise the shadow will be erase.
        (parent as? ViewGroup)?.clipChildren = false
    }

    override fun draw(canvas: Canvas) {
        drawShadow(canvas)
        val saveCount = canvas.saveLayer(null, null)
        try {
            super.draw(canvas)
            clipRadius(canvas)
        } finally {
            canvas.restoreToCount(saveCount)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        shadowDrawable.setBounds(0, 0, w, h)
        setBoxRadius(
            this.topLeftBoxRadius,
            this.topRightBoxRadius,
            this.bottomLeftBoxRadius,
            this.bottomRightBoxRadius
        )
    }

    private fun clipRadius(canvas: Canvas) {
        if (boxRadius > 0
            || topLeftBoxRadius > 0
            || topRightBoxRadius > 0
            || bottomLeftBoxRadius > 0
            || bottomRightBoxRadius > 0
        ) {
            Trace.beginSection("${LOG_TAG}.clipRadius")
            try {
                canvas.drawPath(clipPath, clipPaint)
            } finally {
                Trace.endSection()
            }
        }
    }


    private fun drawShadow(canvas: Canvas) {
        Trace.beginSection("${LOG_TAG}.drawShadow")
        try {
            shadowDrawable.draw(canvas)
        } finally {
            Trace.endSection()
        }
    }


    fun setShadowVerticalOffset(@Px shadowVerticalOffset: Float) {
        this.shadowVerticalOffset = shadowVerticalOffset
        resetShadowOffset()
        invalidate()
    }

    @Px
    fun getShadowVerticalOffset(): Float = this.shadowVerticalOffset

    fun setShadowHorizontalOffset(@Px shadowHorizontalOffset: Float) {
        this.shadowHorizontalOffset = shadowHorizontalOffset
        resetShadowOffset()
        invalidate()
    }

    @Px
    fun getShadowHorizontalOffset(): Float = this.shadowHorizontalOffset

    fun setShadowColor(@ColorInt shadowColor: Int) {
        this.shadowColor = shadowColor
        shadowDrawable.setShadowColor(shadowColor)
        invalidate()
    }

    fun getShadowColor(): Int = this.shadowColor

    fun setShadowBlur(@Px shadowBlur: Float) {
        this.shadowBlur = shadowBlur
        shadowDrawable.setShadowBlur(shadowBlur)
        invalidate()
    }

    fun setShadowInset(shadowInset: Boolean) {
        this.shadowInset = shadowInset
        shadowDrawable.setShadowInset(shadowInset)
        invalidate()
    }


    fun isShadowInset(): Boolean = this.shadowInset

    fun setShadowSpread(@Px shadowSpread: Float) {
        this.shadowSpread = shadowSpread
        resetShadowOffset()
        invalidate()
    }

    @Px
    fun getShadowSpread() = shadowSpread

    fun setBoxRadius(@Px radius: Float) {
        this.boxRadius = radius.absoluteValue
        setBoxRadius(this.boxRadius, this.boxRadius, this.boxRadius, this.boxRadius)
    }


    fun getRadius(): Float = this.boxRadius

    fun setBoxRadius(
        @Px topLeft: Float,
        @Px topRight: Float,
        @Px bottomLeft: Float,
        @Px bottomRight: Float
    ) {
        this.topLeftBoxRadius = topLeft.absoluteValue
        this.topRightBoxRadius = topRight.absoluteValue
        this.bottomLeftBoxRadius = bottomLeft.absoluteValue
        this.bottomRightBoxRadius = bottomRight.absoluteValue
        clipPath.reset()
        clipPath.fillType = Path.FillType.INVERSE_WINDING
        clipPath.addRoundRect2(
            this.topLeftBoxRadius,
            this.topRightBoxRadius,
            this.bottomLeftBoxRadius,
            this.bottomRightBoxRadius,
            width.toFloat(),
            height.toFloat()
        )
        shadowPath.reset()
        shadowPathOffsetX = 0f
        shadowPathOffsetY = 0f
        shadowPath.fillType = Path.FillType.WINDING
        shadowPath.addRoundRect2(
            this.topLeftBoxRadius,
            this.topRightBoxRadius,
            this.bottomLeftBoxRadius,
            this.bottomRightBoxRadius,
            width.toFloat() + shadowSpread * 2,
            height.toFloat() + shadowSpread * 2
        )
        resetShadowOffset()
        invalidate()
    }

    private var shadowPathOffsetX = 0f
    private var shadowPathOffsetY = 0f
    private fun resetShadowOffset() {
        shadowPath.offset(-shadowPathOffsetX, -shadowPathOffsetY)
        shadowPathOffsetX = -shadowSpread + shadowHorizontalOffset
        shadowPathOffsetY = -shadowSpread + shadowVerticalOffset
        shadowPath.offset(shadowPathOffsetX, shadowPathOffsetY)
        shadowDrawable.invalidateCache()
    }

    @Px
    fun getTopLeftRadius() = topLeftBoxRadius

    @Px
    fun getTopRightRadius() = topRightBoxRadius

    @Px
    fun getBottomRightRadius() = bottomRightBoxRadius

    @Px
    fun getBottomLeftRadius() = bottomLeftBoxRadius


}

private fun Path.addRoundRect2(tL: Float, tR: Float, bL: Float, bR: Float, w: Float, h: Float) {
    val radii = floatArrayOf(tL, tL, tR, tR, bR, bR, bL, bL)
    addRoundRect(0f, 0f, w, h, radii, Path.Direction.CW)
}
