package pokercc.android.boxshadowlayout

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.annotation.CallSuper

internal abstract class ShadowDrawable(protected val shadowPath: Path) : Drawable() {

    override fun setAlpha(alpha: Int) = Unit
    override fun setColorFilter(colorFilter: ColorFilter?) = Unit
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
    protected var shadowBlur: Float = 0f
        private set

    protected var shadowColor: Int = Color.TRANSPARENT
        private set

    protected var shadowInset: Boolean = false
        private set

    @CallSuper
    open fun setShadowBlur(blur: Float) {
        this.shadowBlur = blur
        onShadowChange(this.shadowBlur, this.shadowColor, this.shadowInset)
    }

    @CallSuper
    open fun setShadowColor(color: Int) {
        this.shadowColor = color
        onShadowChange(this.shadowBlur, this.shadowColor, this.shadowInset)
    }

    @CallSuper
    open fun setShadowInset(inset: Boolean) {
        this.shadowInset = inset
        onShadowChange(this.shadowBlur, this.shadowColor, this.shadowInset)
    }

    open fun invalidateCache() = Unit

    abstract fun onShadowChange(blur: Float, color: Int, inset: Boolean)

}