package pokercc.android.boxshadowlayout

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur


internal class RenderScriptShadowDrawable(private val context: Context, shadowPath: Path) :
    BitmapShadowDrawable(shadowPath) {
    companion object {
        private const val LOG_TAG = "RenderScriptShadow"
    }

    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
    }


    override fun onDrawBitmap(bitmap: Bitmap) {


    }

    override fun onShadowChange(blur: Float, color: Int, inset: Boolean) {
    }


    private fun blurBitmap(
        context: Context,
        originBitmap: Bitmap,
        blurBitmap: Bitmap,
        radius: Float
    ) {

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
    }


}
