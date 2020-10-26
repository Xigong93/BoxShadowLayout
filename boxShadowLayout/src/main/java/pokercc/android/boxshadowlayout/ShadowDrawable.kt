package pokercc.android.boxshadowlayout

import android.graphics.ColorFilter
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

internal abstract class ShadowDrawable(val shadowPath: Path) : Drawable() {
    abstract var shadowBlur: Float
    abstract var shadowColor: Int
    abstract var shadowInset: Boolean
    override fun setAlpha(alpha: Int) = Unit
    override fun setColorFilter(colorFilter: ColorFilter?) = Unit
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}