package pokercc.android.boxshadowlayout.demo

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pokercc.android.boxshadowlayout.demo.databinding.NumberItemBinding

internal class NumberItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val padding = Dp(10).toPx().toInt()
        outRect.set(padding, padding, padding, padding)
    }
}

internal class NumberVH(val binding: NumberItemBinding) : RecyclerView.ViewHolder(binding.root)

internal class NumberAdapter(private val count: Int) : RecyclerView.Adapter<NumberVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberVH {
        return LayoutInflater.from(parent.context)
            .let { NumberItemBinding.inflate(it, parent, false) }
            .let(::NumberVH)
    }

    override fun getItemCount(): Int = count

    override fun onBindViewHolder(holder: NumberVH, position: Int) {
        holder.binding.title.text = (position + 1).toString()
    }

}