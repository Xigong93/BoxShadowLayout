package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.*
import android.os.Trace
import android.util.Log
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur


internal class RenderScriptShadowDrawable(private val context: Context, shadowPath: Path) :
    ShadowDrawable(shadowPath) {
    companion object {
        private const val LOG_TAG = "RenderScriptShadow"
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
        val rawBitmap = rawBitmap ?: return
        val blurBitmap = blurBitmap ?: return
        val bitmapCanvas = Canvas(rawBitmap)
        bitmapCanvas.drawPath(shadowPath, shadowPaint)
        blurBitmap(context, rawBitmap, blurBitmap, shadowBlur)
        canvas.drawBitmap(blurBitmap, bounds.left.toFloat(), bounds.top.toFloat(), null)
        Trace.endSection()
    }

    private var rawBitmap: Bitmap? = null
    private var blurBitmap: Bitmap? = null
    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        rawBitmap?.recycle()
        rawBitmap = createBitmap(bounds)
        blurBitmap?.recycle()
        blurBitmap = createBitmap(bounds)
    }

    private fun createBitmap(bounds: Rect): Bitmap {
        return Bitmap.createBitmap(
            ((bounds.width() + shadowBlur * 2)).toInt(),
            ((bounds.height() + shadowBlur * 2)).toInt(),
            Bitmap.Config.ARGB_8888
        )
    }

    private fun blurBitmap(
        context: Context,
        originBitmap: Bitmap,
        blurBitmap: Bitmap,
        radius: Float
    ) {
        val startTime = System.currentTimeMillis()
        Log.d(
            LOG_TAG,
            "blurBitmap,width:${originBitmap.width},height:${originBitmap.height},radius:${radius}"
        )
        Trace.beginSection("${LOG_TAG}:blurBitmap")
        val renderScript = RenderScript.create(context, RenderScript.ContextType.PROFILE)
        val allocation = Allocation.createFromBitmap(
            renderScript, originBitmap,
            Allocation.MipmapControl.MIPMAP_NONE,
            Allocation.USAGE_SCRIPT
        )
        val output = Allocation.createTyped(renderScript, allocation.type)
        val script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        script.setRadius(radius)
        script.setInput(allocation)
        script.forEach(output)
        output.copyTo(blurBitmap)
        renderScript.destroy()
        Trace.endSection()
        Log.d(LOG_TAG, "blurBitmap,passed ${System.currentTimeMillis() - startTime}ms")
    }


}
