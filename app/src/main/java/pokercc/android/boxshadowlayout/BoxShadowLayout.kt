package pokercc.android.boxshadowlayout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Box Shadow like css in web.
 */
class BoxShadowLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        context.obtainStyledAttributes(
            attrs, R.styleable.BoxShadowLayout, defStyle, 0
        ).apply {

        }.recycle()

    }

}
