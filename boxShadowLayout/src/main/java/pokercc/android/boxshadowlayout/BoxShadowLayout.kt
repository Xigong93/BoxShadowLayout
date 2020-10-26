package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import kotlin.math.absoluteValue

/**
 * Box Shadow like css in web.
 * @author pokercc
 * @date 2020-10-20 00:10:19
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
    private var boxRadius = 0f
    private var topLeftBoxRadius = 0f
    private var topRightBoxRadius = 0f
    private var bottomLeftBoxRadius = 0f
    private var bottomRightBoxRadius = 0f
    private val clipPath = Path()
    private val clipPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val shadowPath = Path()

    private val shadowDrawable: ShadowDrawable =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BlurMaskShadowDrawable(shadowPath)
        } else {
            RenderScriptShadowDrawable(context, shadowPath)
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
            canvas.drawPath(clipPath, clipPaint)
        }
    }


    private fun drawShadow(canvas: Canvas) {
        shadowDrawable.draw(canvas)
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
        shadowDrawable.shadowColor = shadowColor
        invalidate()
    }

    fun getShadowColor(): Int = this.shadowColor

    fun setShadowBlur(shadowBlur: Float) {
        this.shadowBlur = shadowBlur
        shadowDrawable.shadowBlur = shadowBlur
        invalidate()
    }

    fun setShadowInset(shadowInset: Boolean) {
        this.shadowInset = shadowInset
        shadowDrawable.shadowInset = shadowInset
        invalidate()
    }


    fun isShadowInset(): Boolean = this.shadowInset

    fun setShadowSpread(shadowSpread: Float) {
        this.shadowSpread = shadowSpread
        invalidate()
    }

    fun getShadowSpread() = shadowSpread

    fun setBoxRadius(radius: Float) {
        this.boxRadius = radius.absoluteValue
        setBoxRadius(this.boxRadius, this.boxRadius, this.boxRadius, this.boxRadius)
    }


    fun getRadius(): Float = this.boxRadius

    fun setBoxRadius(topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
        this.topLeftBoxRadius = topLeft.absoluteValue
        this.topRightBoxRadius = topRight.absoluteValue
        this.bottomLeftBoxRadius = bottomLeft.absoluteValue
        this.bottomRightBoxRadius = bottomRight.absoluteValue
        clipPath.reset()
        clipPath.fillType = Path.FillType.INVERSE_WINDING
        clipPath.setRoundRect(
            this.topLeftBoxRadius,
            this.topRightBoxRadius,
            this.bottomLeftBoxRadius,
            this.bottomRightBoxRadius,
            width.toFloat(),
            height.toFloat()
        )
        shadowPath.reset()
        shadowPath.fillType = Path.FillType.WINDING
        shadowPath.setRoundRect(
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

    private fun resetShadowOffset() {
        shadowPath.offset(
            -shadowSpread + shadowHorizontalOffset,
            -shadowSpread + shadowVerticalOffset
        )

    }

    fun getTopLeftRadius() = topLeftBoxRadius
    fun getTopRightRadius() = topRightBoxRadius
    fun getBottomRightRadius() = bottomRightBoxRadius
    fun getBottomLeftRadius() = bottomLeftBoxRadius


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