package pokercc.android.boxshadowlayout.demo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnPreDraw
import pokercc.android.boxshadowlayout.demo.databinding.AnimationActivityBinding

class AnimationActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AnimationActivity::class.java))
        }
    }

    private val binding by lazy { AnimationActivityBinding.inflate(layoutInflater) }
    private var anim: AnimatorSet? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.performanceButton.setOnClickListener {
            PerformanceActivity.start(it.context)
        }
        binding.root.doOnPreDraw {
            startAnimation()
        }
    }

    private fun startAnimation() {
        // Blur Animation
        // Color Animation
        // Radius Animation
        // Offset Animation
        val blurColorAnim = ObjectAnimator.ofArgb(
            binding.boxShadowLayout,
            "ShadowColor",
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.BLUE,
            Color.DKGRAY,
            Color.BLACK
        )
        val blurRadiusAnim = ObjectAnimator.ofFloat(
            binding.boxShadowLayout,
            "ShadowBlur",
            Dp(0f).toPx().toFloat(),
            Dp(80f).toPx().toFloat()
        )
        val boxRadiusAnim = ObjectAnimator.ofFloat(
            binding.boxShadowLayout,
            "boxRadius",
            Dp(0f).toPx().toFloat(),
            Dp(80f).toPx().toFloat()
        )
        val horizontalOffsetAnim = ObjectAnimator.ofFloat(
            binding.boxShadowLayout,
            "ShadowHorizontalOffset",
            Dp(0f).toPx().toFloat(),
            Dp(20f).toPx().toFloat()
        )
        val verticalOffsetAnim = ObjectAnimator.ofFloat(
            binding.boxShadowLayout,
            "ShadowHorizontalOffset",
            Dp(0f).toPx().toFloat(),
            Dp(20f).toPx().toFloat()
        )
        anim?.cancel()
        val animatorSet = AnimatorSet()
        anim = animatorSet
        animatorSet.duration = 2000
        animatorSet.playSequentially(
            blurColorAnim,
            blurRadiusAnim,
            boxRadiusAnim,
            horizontalOffsetAnim,
            verticalOffsetAnim
        )
        animatorSet.start()
        animatorSet.doOnEnd {
            startAnimation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        anim?.cancel()
    }
}