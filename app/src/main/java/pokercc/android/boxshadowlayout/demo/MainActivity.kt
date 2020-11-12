package pokercc.android.boxshadowlayout.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pokercc.android.boxshadowlayout.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.testButton.setOnClickListener {
            TestActivity.start(
                this,
                binding.boxShadowLayout,
                binding.boxShadowLayout.transitionName
            )
        }
    }
}