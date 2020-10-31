package pokercc.android.boxshadowlayout

import android.graphics.*
import android.os.Trace
import android.util.Log


internal class FastBlurShadowDrawable(shadowPath: Path) :
    ShadowDrawable(shadowPath) {
    companion object {
        private const val LOG_TAG = "FastBlurShadowDrawable"
    }

    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    override var shadowBlur: Float = 0f
    override var shadowInset: Boolean = false
    override var shadowColor: Int = Color.WHITE
        set(value) {
            field = value
            shadowPaint.color = value
        }

    override fun draw(canvas: Canvas) {
        Trace.beginSection("${LOG_TAG}:draw")
        val rawBitmap = bitmap ?: return
        val bitmapCanvas = Canvas(rawBitmap)
        bitmapCanvas.drawPath(shadowPath, shadowPaint)
        val blurBitmap = rawBitmap.fastBlur(shadowBlur)
        canvas.drawBitmap(blurBitmap, bounds.left.toFloat(), bounds.top.toFloat(), null)
        Trace.endSection()
    }

    private var bitmap: Bitmap? = null
    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        bitmap?.recycle()
        bitmap = createBitmap(bounds)
    }

    private fun createBitmap(bounds: Rect): Bitmap {
        return Bitmap.createBitmap(
            ((bounds.width() + shadowBlur * 2)).toInt(),
            ((bounds.height() + shadowBlur * 2)).toInt(),
            Bitmap.Config.ARGB_8888
        )
    }

    private fun Bitmap.fastBlur(radius: Float): Bitmap {
        val startTime = System.currentTimeMillis()
        Log.d(
            LOG_TAG,
            "fastBlur,width:${width},height:${height},radius:${radius}"
        )
        Trace.beginSection("${LOG_TAG}:fastBlur")
        val bitmap = FastBlur.fastBlur(this, radius.toInt())
        Trace.endSection()
        Log.d(LOG_TAG, "fastBlur,passed ${System.currentTimeMillis() - startTime}ms")
        return bitmap
    }
}
