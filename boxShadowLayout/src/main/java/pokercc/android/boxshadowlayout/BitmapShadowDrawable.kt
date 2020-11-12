package pokercc.android.boxshadowlayout

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import androidx.annotation.CallSuper

internal abstract class BitmapShadowDrawable(shadowPath: Path) : ShadowDrawable(shadowPath) {


    private var bitmap: Bitmap? = null
    private var bitmapDrawOver = false
    final override fun draw(canvas: Canvas) {
        val rawBitmap = bitmap ?: return
        drawBitmap(rawBitmap)
        canvas.drawBitmap(rawBitmap, -shadowBlur * 2, -shadowBlur * 2, null)
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
            ((bounds.width() + shadowBlur * 4)).toInt(),
            ((bounds.height() + shadowBlur * 4)).toInt(),
            Bitmap.Config.ARGB_8888
        )
        invalidateCache()
    }

    override fun invalidateCache() {
        bitmapDrawOver = false

    }
    private fun drawBitmap(bitmap: Bitmap) {
        if (bitmapDrawOver) return
        onDrawBitmap(bitmap)
        bitmapDrawOver = true
    }

    @CallSuper
    override fun onShadowChange(blur: Float, color: Int, inset: Boolean) {
        invalidateCache()
    }

    abstract fun onDrawBitmap(bitmap: Bitmap)


}