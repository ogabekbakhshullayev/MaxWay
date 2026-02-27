package uz.gita.maxwayappclone.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ItemFilterChipBinding
import uz.gita.maxwayappclone.domain.model.Category

class FilterChipAdapter(
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<FilterChipAdapter.ViewHolder>() {

    private val items = ArrayList<Category>()
    private var selectedId: Long? = null

    fun submitList(list: List<Category>, selected: Long?) {
        items.clear()
        items.addAll(list)
        selectedId = selected
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterChipBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], items[position].id == selectedId, onClick)
    }

    class ViewHolder(private val binding: ItemFilterChipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, isSelected: Boolean, onClick: (Category) -> Unit) {
            binding.tvFilter.text = item.name
            val bg = if (isSelected) R.drawable.bg_filter_chip else R.drawable.bg_chip_unselected
            binding.tvFilter.setBackgroundResource(bg)
            binding.tvFilter.setOnClickListener { onClick(item) }
        }
    }
}
