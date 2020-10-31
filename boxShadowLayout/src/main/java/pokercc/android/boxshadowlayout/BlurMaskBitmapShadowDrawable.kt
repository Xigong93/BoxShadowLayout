package pokercc.android.boxshadowlayout

import android.graphics.*

internal class BlurMaskBitmapShadowDrawable(shadowPath: Path) : BitmapShadowDrawable(shadowPath) {
    private companion object {
        const val LOG_TAG = "BlurMaskBitmap"
    }

    private val shadowPaint = Paint().apply {
        isDither = true
        isAntiAlias = true
        style = Paint.Style.FILL
    }


    override fun onDrawBitmap(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.translate(shadowBlur * 2, shadowBlur * 2)
        canvas.drawPath(shadowPath, shadowPaint)
    }

    override fun onShadowChange(blur: Float, color: Int, inset: Boolean) {
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
        shadowPaint.color = color
    }

}