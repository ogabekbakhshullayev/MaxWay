package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.databinding.ItemBasketProductBinding
import uz.gita.maxwayappclone.domain.model.Product
import uz.gita.maxwayappclone.presentation.screens.basket.BasketItem
import uz.gita.maxwayappclone.utils.formatPrice
import uz.gita.maxwayappclone.utils.loadImageWithGlide

// tavsiya listadapter ishlatish
class BasketAdapter() : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private var onCountChangeListener: ((Product, Int) -> Unit)?= null
    private val items = ArrayList<BasketItem>()

    inner class BasketViewHolder(private val binding: ItemBasketProductBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnPlus.setOnClickListener {
                onCountChangeListener?.invoke(items[absoluteAdapterPosition].product, items[absoluteAdapterPosition].count + 1)
            }

            binding.btnMinus.setOnClickListener {
                val next = if (items[absoluteAdapterPosition].count <= 1) 0 else items[absoluteAdapterPosition].count - 1
                onCountChangeListener?.invoke(items[absoluteAdapterPosition].product, next)
            }
        }

        fun bind(item: BasketItem) {
            binding.ivProduct.loadImageWithGlide(item.product.image)
            binding.tvName.text = item.product.name
            binding.tvPrice.text = item.product.cost.formatPrice()
            binding.tvQty.text = item.count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder =
        BasketViewHolder(ItemBasketProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<BasketItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnCountChangeListener(block: (Product, Int) -> Unit) {
        onCountChangeListener = block
    }
}