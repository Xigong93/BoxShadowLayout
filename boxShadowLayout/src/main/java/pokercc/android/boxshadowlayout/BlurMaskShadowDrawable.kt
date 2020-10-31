package pokercc.android.boxshadowlayout

import android.graphics.*

internal class BlurMaskShadowDrawable(shadowPath: Path) : ShadowDrawable(shadowPath) {
    private val shadowPaint = Paint().apply {
        isDither = true
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(shadowPath, shadowPaint)
    }

    override fun onShadowChange(blur: Float, color: Int, inset: Boolean) {
        val type = if (shadowInset) {
            BlurMaskFilter.Blur.INNER
        } else {
            BlurMaskFilter.Blur.NORMAL
        }
        shadowPaint.maskFilter = if (shadowBlur == 0f) {
            null
        } else {
            BlurMaskFilter(shadowBlur, type)
        }
        shadowPaint.color = color
    }
}