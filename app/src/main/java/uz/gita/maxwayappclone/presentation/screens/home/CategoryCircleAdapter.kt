package uz.gita.maxwayappclone.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ItemCategoryCircleBinding
import uz.gita.maxwayappclone.domain.model.Category

class CategoryCircleAdapter(
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryCircleAdapter.ViewHolder>() {

    private val items = ArrayList<Category>()

    fun submitList(list: List<Category>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryCircleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    class ViewHolder(private val binding: ItemCategoryCircleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, onClick: (Category) -> Unit) {
            binding.tvCategory.text = item.name
            binding.ivCategory.setImageResource(R.drawable.burger)
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
