package pokercc.android.boxshadowlayout.demo

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pokercc.android.boxshadowlayout.demo.databinding.NumberItemBinding
import pokercc.android.boxshadowlayout.demo.databinding.PerformanceActivityBinding

class PerformanceActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, PerformanceActivity::class.java))
        }
    }

    private val binding by lazy { PerformanceActivityBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    private val columns = 4
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, columns)
        binding.recyclerView.adapter = NumberAdapter(999)
        binding.recyclerView.addItemDecoration(NumberItemDecoration())
    }
}

