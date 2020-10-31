package pokercc.android.boxshadowlayout

import android.graphics.*
import android.os.Trace
import androidx.annotation.CallSuper

internal abstract class BitmapShadowDrawable(shadowPath: Path) : ShadowDrawable(shadowPath) {

    companion object {
        private const val LOG_TAG = "BitmapShadowDrawable"
    }

    private var bitmap: Bitmap? = null
    private var bitmapDrawOver = false
    final override fun draw(canvas: Canvas) {
        Trace.beginSection("${LOG_TAG}:draw")
        try {
            val rawBitmap = bitmap ?: return
            drawBitmap(rawBitmap)
            canvas.drawBitmap(rawBitmap, -shadowBlur, -shadowBlur, null)
        } finally {
            Trace.endSection()
        }
    }


    @CallSuper
    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        bitmapDrawOver = false
        createBitmap()
    }

    @CallSuper
    override fun setShadowBlur(blur: Float) {
        super.setShadowBlur(blur)
        bitmapDrawOver = false
        createBitmap()
    }

    private fun createBitmap() {
        bitmap?.recycle()
        bitmap = Bitmap.createBitmap(
            ((bounds.width() + shadowBlur * 2)).toInt(),
            ((bounds.height() + shadowBlur * 2)).toInt(),
            Bitmap.Config.ARGB_8888
        )
    }

    private fun drawBitmap(bitmap: Bitmap) {
        if (bitmapDrawOver) return
        onDrawBitmap(bitmap)
        bitmapDrawOver = true
    }

    abstract fun onDrawBitmap(bitmap: Bitmap)

}