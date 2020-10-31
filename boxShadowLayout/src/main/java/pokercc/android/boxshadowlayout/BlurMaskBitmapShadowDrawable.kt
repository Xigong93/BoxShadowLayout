package pokercc.android.boxshadowlayout

import android.graphics.*
import android.os.Trace

internal class BlurMaskBitmapShadowDrawable(shadowPath: Path) : ShadowDrawable(shadowPath) {
    private companion object {
        const val LOG_TAG = "BlurMaskBitmap"
    }

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

    private var bitmapDrawOver = false
    override fun draw(canvas: Canvas) {
        Trace.beginSection("${LOG_TAG}:draw")
        val rawBitmap = bitmap ?: return
        if (!bitmapDrawOver) {
            drawBitmap()
            bitmapDrawOver = true
        }
        canvas.drawBitmap(rawBitmap, bounds.left.toFloat(), bounds.top.toFloat(), null)
        Trace.endSection()
    }

    private var bitmap: Bitmap? = null
    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        bitmap?.recycle()
        bitmap = createBitmap(bounds)
        drawBitmap()
    }

    private fun drawBitmap() {
        val rawBitmap = bitmap ?: return
        val bitmapCanvas = Canvas(rawBitmap)
        bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        bitmapCanvas.drawPath(shadowPath, shadowPaint)
    }

    private fun createBitmap(bounds: Rect): Bitmap {
        return Bitmap.createBitmap(
            ((bounds.width() + shadowBlur * 2)).toInt(),
            ((bounds.height() + shadowBlur * 2)).toInt(),
            Bitmap.Config.ARGB_8888
        )
    }

}