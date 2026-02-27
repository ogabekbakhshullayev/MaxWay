package uz.gita.maxwayappclone.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.databinding.ItemHomeProductBinding
import uz.gita.maxwayappclone.domain.model.Product
class HomeProductAdapter : RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

    private val items = ArrayList<Product>()

    fun submitList(list: List<Product>) {
        items.clear()
        items.addAll(list)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val binding: ItemHomeProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            Glide.with(binding.root.context)
                .load(item.image)
                .centerCrop()
                .into(binding.ivProduct)

            binding.tvName.text = item.name
            binding.tvDesc.text = item.description
            binding.tvPrice.text = formatPrice(item.cost)
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
