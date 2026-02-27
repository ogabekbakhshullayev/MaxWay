package uz.gita.maxwayappclone.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.databinding.ItemStoryCircleBinding
import uz.gita.maxwayappclone.data.source.remote.response.StoryData

class StoryAdapter(
    private val onClick: (StoryData) -> Unit
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private val items = ArrayList<StoryData>()

    fun submitList(list: Array<StoryData>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryCircleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    class ViewHolder(private val binding: ItemStoryCircleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoryData, onClick: (StoryData) -> Unit) {
            binding.tvStoryName.text = item.name
            Glide.with(binding.root.context)
                .load(item.url)
                .centerCrop()
                .into(binding.ivStory)
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
