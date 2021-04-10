package pokercc.android.boxshadowlayout

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import androidx.annotation.CallSuper

internal abstract class BitmapShadowDrawable(shadowPath: Path) : ShadowDrawable(shadowPath) {
    companion object {
        private const val LOG_TAG = "BitmapShadowDrawable"
    }


    private var bitmap: Bitmap? = null
    private var bitmapDrawOver = false
    final override fun draw(canvas: Canvas) {
        createBitmap()
        val rawBitmap = bitmap ?: return
        drawBitmap(rawBitmap)
        canvas.drawBitmap(rawBitmap, -shadowBlur * 2, -shadowBlur * 2, null)
    }


    @CallSuper
    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        invalidateCache()
    }

    @CallSuper
    override fun setShadowBlur(blur: Float) {
        super.setShadowBlur(blur)
        invalidateCache()
    }

    private fun createBitmap() {
        val newWidth = ((bounds.width() + shadowBlur * 4)).toInt()
        val newHeight = ((bounds.height() + shadowBlur * 4)).toInt()
        val oldBitmap = bitmap
        if (oldBitmap != null && oldBitmap.width >= newWidth && oldBitmap.height >= newHeight) {
            return
        }
        oldBitmap?.recycle()
        bitmap = Bitmap.createBitmap(
            newWidth,
            newHeight,
            Bitmap.Config.ARGB_8888
        )
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "allocate bitmap (${bitmap?.width}x${bitmap?.height})")
        }
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