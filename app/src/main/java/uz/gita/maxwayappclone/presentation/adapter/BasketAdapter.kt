package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.databinding.ItemBasketProductBinding
import uz.gita.maxwayappclone.utils.formatPrice
import uz.gita.maxwayappclone.utils.loadImageWithGlide

class BasketAdapter() : ListAdapter<ProductUIData, BasketAdapter.BasketViewHolder>(BasketDiffUtil) {

    private var onChangeProductCountListener: (() -> Unit)?= null

    object BasketDiffUtil : DiffUtil.ItemCallback<ProductUIData>() {
        override fun areItemsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean {
            return oldItem == newItem
        }
    }

    inner class BasketViewHolder(private val binding: ItemBasketProductBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnPlus.setOnClickListener {
                getItem(absoluteAdapterPosition).count ++
                onChangeProductCountListener?.invoke()
            }

            binding.btnMinus.setOnClickListener {
                getItem(absoluteAdapterPosition).count --
                onChangeProductCountListener?.invoke()
            }
        }

        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.ivProduct.loadImageWithGlide(this.image)
                binding.tvName.text = this.name
                binding.tvPrice.text = this.cost.formatPrice()
                binding.tvQty.text = this.count.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(ItemBasketProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnChangeProductCountListener(block: () -> Unit) {
        onChangeProductCountListener = block
    }
}