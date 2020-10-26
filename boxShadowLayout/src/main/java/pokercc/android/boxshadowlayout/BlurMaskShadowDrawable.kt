package pokercc.android.boxshadowlayout

import android.graphics.*

internal class BlurMaskShadowDrawable(shadowPath: Path) : ShadowDrawable(shadowPath) {
    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
    }


    private fun resetBlur() {
        val type = if (this.shadowInset) {
            BlurMaskFilter.Blur.INNER
        } else {
            BlurMaskFilter.Blur.NORMAL
        }
        shadowPaint.maskFilter = if (this.shadowBlur == 0f) {
            null
        } else {
            BlurMaskFilter(this.shadowBlur, type)
        }
    }


    override var shadowBlur: Float = 0f
        set(value) {
            field = value
            resetBlur()
        }
    override var shadowInset: Boolean = false
        set(value) {
            field = value
            resetBlur()
        }
    override var shadowColor: Int = Color.WHITE
        set(value) {
            field = value
            shadowPaint.color = value
        }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(shadowPath, shadowPaint)
    }

}