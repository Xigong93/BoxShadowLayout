package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Box Shadow like css in web.
 */
class BoxShadowLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    companion object {
        private const val LOG_TAG = "BoxShadowLayout"
        private const val DEBUG = false
    }

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

    private var radius = 0f

    //        private val shadowColor = 0x5cc7ccde.toInt()
    private val shadowColor = Color.RED
    private var blur = 5f * 3
    private val shadowPaint = Paint().apply {
        color = shadowColor
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(blur, BlurMaskFilter.Blur.OUTER)
    }
    private val debugPaint = Paint().apply {
        color = Color.BLACK
    }

    init {
//        setPadding((blur * 2).toInt())
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Tell parent don't clip me. Otherwise the shadow will be erase.
        (parent as? ViewGroup)?.clipChildren = false
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawShadow(canvas)

    }

    private val target: View? get() = getChildAt(0)
    private fun drawShadow(canvas: Canvas) {

//        canvas.drawColor(Color.YELLOW)
        if (DEBUG) {
            canvas.drawLine(60f, 0f, width - 60f, 0f, debugPaint)
            canvas.drawLine(
                60f,
                height.toFloat() - 1,
                width - 60f,
                height.toFloat() - 1,
                debugPaint
            )
        }
        target?.let {
            canvas.drawRoundRect(
                it.x,
                it.y,
                it.x + it.width.toFloat(),
                it.y + it.height.toFloat(),
                radius,
                radius,
                shadowPaint
            )

        }
    }


}
