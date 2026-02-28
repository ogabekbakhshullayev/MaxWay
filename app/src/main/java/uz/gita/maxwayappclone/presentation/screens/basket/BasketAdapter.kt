package uz.gita.maxwayappclone.presentation.screens.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.databinding.ItemBasketProductBinding
import uz.gita.maxwayappclone.domain.model.Product

class BasketAdapter(
    private val onCountChange: (Product, Int) -> Unit
) : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private val items = ArrayList<BasketItem>()

    fun submitList(list: List<BasketItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBasketProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onCountChange)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemBasketProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BasketItem, onCountChange: (Product, Int) -> Unit) {
            val product = item.product
            val count = item.count

            Glide.with(binding.root.context)
                .load(product.image)
                .centerInside()
                .into(binding.ivProduct)

            binding.tvName.text = product.name
            binding.tvPrice.text = formatPrice(product.cost)
            binding.tvQty.text = count.toString()

            binding.btnPlus.setOnClickListener { onCountChange(product, count + 1) }
            binding.btnMinus.setOnClickListener {
                val next = if (count <= 1) 0 else count - 1
                onCountChange(product, next)
            }
        }

        private fun formatPrice(cost: Long): String {
            val raw = cost.toString()
            val sb = StringBuilder()
            for (i in raw.indices) {
                sb.append(raw[i])
                val remaining = raw.length - i - 1
                if (remaining > 0 && remaining % 3 == 0) sb.append(' ')
            }
            return "${sb} сум"
        }
    }
}
