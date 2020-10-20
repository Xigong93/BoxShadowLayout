package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Box Shadow like css in web.
 */
class BoxShadowLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {


    init {
        setWillNotDraw(false)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        context.obtainStyledAttributes(
            attrs, R.styleable.BoxShadowLayout, defStyle, 0
        ).apply {

        }.recycle()

    }

    private val shadowColor = 0xffc7ccde.toInt()
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xffc7ccde.toInt()
        maskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.INNER)
        shader = LinearGradient(
            0f,
            0f,
            300f,
            0f,
            shadowColor,
            0xFFFF7D39.toInt(),
            Shader.TileMode.MIRROR
        )
        this.strokeWidth = shadowWidth
        this.style = Paint.Style.FILL
    }
    private val shadowWidth = 15f
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Tell parent don't clip me. Otherwise the shadow will be erase.
        (parent as? ViewGroup)?.clipChildren = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(
            shadowWidth * -1,
            shadowWidth * -1,
            width + shadowWidth,
            height + shadowWidth,
            0f,
            0f,
            shadowPaint
        )
    }


}
