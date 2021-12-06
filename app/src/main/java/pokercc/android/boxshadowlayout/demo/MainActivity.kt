package pokercc.android.boxshadowlayout.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pokercc.android.boxshadowlayout.demo.databinding.MainActivityBinding as Binding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { Binding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bindView()
    }

    private fun Binding.bindView() {
        attrButton.setOnClickListener {
            val shareView = binding.boxShadowLayout
            AttrActivity.start(this@MainActivity, shareView, shareView.transitionName)
        }
        animButton.setOnClickListener {
            AnimationActivity.start(it.context)
        }
        clipButton.setOnClickListener {
            ClipActivity.start(it.context)
        }
        performanceButton.setOnClickListener {
            PerformanceActivity.start(it.context)
        }
    }


}