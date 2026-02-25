package uz.gita.maxwayappclone.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ItemHomeHeaderBinding
import uz.gita.maxwayappclone.databinding.ItemHomeProductBinding
import uz.gita.maxwayappclone.domain.model.Product

sealed class HomeItem {
    data class Header(val categoryId: Long, val title: String) : HomeItem()
    data class ProductItem(val product: Product) : HomeItem()
}

class HomeSectionAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onCountChange: (Product, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<HomeItem>()
    private var productCounts: Map<Long, Int> = emptyMap()

    fun submitList(list: List<HomeItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun updateCounts(counts: Map<Long, Int>) {
        productCounts = counts
        notifyDataSetChanged()
    }

    fun getCategoryForPosition(position: Int): Long? {
        for (i in position downTo 0) {
            val item = items.getOrNull(i) ?: continue
            if (item is HomeItem.Header) return item.categoryId
            if (item is HomeItem.ProductItem) return item.product.categoryId
        }
        return null
    }

    fun getPositionForCategory(categoryId: Long): Int {
        return items.indexOfFirst { it is HomeItem.Header && it.categoryId == categoryId }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeItem.Header -> 0
            is HomeItem.ProductItem -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemHomeHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            HeaderHolder(binding)
        } else {
            val binding = ItemHomeProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ProductHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeItem.Header -> (holder as HeaderHolder).bind(item)
            is HomeItem.ProductItem -> (holder as ProductHolder).bind(
                item.product,
                onProductClick,
                onCountChange,
                productCounts[item.product.id] ?: 0
            )
        }
    }

    class HeaderHolder(private val binding: ItemHomeHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItem.Header) {
            binding.tvHeader.text = item.title
            binding.root.setTag(R.id.tag_category_id, item.categoryId)
        }
    }

    class ProductHolder(private val binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Product,
            onProductClick: (Product) -> Unit,
            onCountChange: (Product, Int) -> Unit,
            count: Int
        ) {
            Glide.with(binding.root.context)
                .load(item.image)
                .centerCrop()
                .into(binding.ivProduct)

            binding.tvName.text = item.name
            binding.tvDesc.text = item.description
            binding.tvPrice.text = formatPrice(item.cost)
            binding.root.setOnClickListener { onProductClick(item) }

            if (count > 0) {
                binding.btnAdd.visibility = android.view.View.GONE
                binding.countContainer.visibility = android.view.View.VISIBLE
                binding.tvQty.text = count.toString()
            } else {
                binding.btnAdd.visibility = android.view.View.VISIBLE
                binding.countContainer.visibility = android.view.View.GONE
            }

            binding.btnAdd.setOnClickListener { onCountChange(item, 1) }
            binding.btnPlus.setOnClickListener { onCountChange(item, count + 1) }
            binding.btnMinus.setOnClickListener {
                val next = if (count <= 1) 0 else count - 1
                onCountChange(item, next)
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
