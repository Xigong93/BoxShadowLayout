package pokercc.android.boxshadowlayout.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pokercc.android.boxshadowlayout.demo.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.attrButton.setOnClickListener {
            AttrActivity.start(
                this,
                binding.boxShadowLayout,
                binding.boxShadowLayout.transitionName
            )
        }
    }
}