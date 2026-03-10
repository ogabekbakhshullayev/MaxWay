package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.databinding.ItemRecommendProductBinding
import uz.gita.maxwayappclone.presentation.util.toFormatted

class RecommendAdapter : ListAdapter<ProductUIData, RecommendAdapter.ViewHolder>(diff) {
    private var listener: ((ProductUIData) -> Unit)? = null
    fun onClickItem(l: (ProductUIData) -> Unit) {
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(private val binding: ItemRecommendProductBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { listener?.invoke(getItem(absoluteAdapterPosition)) }
        }

        fun bind() {
            val data = getItem(absoluteAdapterPosition)
            Glide.with(binding.root.context)
                .load(data.image)
                .centerCrop()
                .into(binding.ivProduct)

            binding.tvName.text = data.name
            binding.tvDesc.text = data.description
            binding.tvPrice.text = "${data.cost.toFormatted()} сум"

//            binding.btnAdd.setOnClickListener {
//                data.count = 1
//                binding.btnAdd.visibility = android.view.View.GONE
//                binding.countContainer.visibility = android.view.View.VISIBLE
//                binding.tvQty.text = data.count.toString()
//
//                onCountChange?.invoke(data, 1)
//            }
//            binding.btnPlus.setOnClickListener {
//                binding.tvQty.text = (++data.count).toString()
////                onCountChange?.invoke(data, data.count)
//            }
//            binding.btnMinus.setOnClickListener {
//                val next = if (data.count <= 1) 0 else data.count - 1
//                data.count = next
//                if (data.count > 0) {
//                    binding.btnAdd.visibility = android.view.View.GONE
//                    binding.countContainer.visibility = android.view.View.VISIBLE
//                    binding.tvQty.text = data.count.toString()
//                } else {
//                    binding.btnAdd.visibility = android.view.View.VISIBLE
//                    binding.countContainer.visibility = android.view.View.GONE
//                }
//
////                onCountChange?.invoke(data, next)
//            }
        }
    }

    companion object {
        val diff = object: DiffUtil.ItemCallback<ProductUIData>() {
            override fun areItemsTheSame(p0: ProductUIData, p1: ProductUIData) =
                p0.id == p1.id

            override fun areContentsTheSame(p0: ProductUIData, p1: ProductUIData) =
                p0 == p1
        }
    }
}
