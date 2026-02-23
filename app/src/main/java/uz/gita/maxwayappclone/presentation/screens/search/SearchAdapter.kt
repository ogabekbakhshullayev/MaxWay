package uz.gita.maxwayappclone.presentation.screens.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse
import uz.gita.maxwayappclone.databinding.ItemProductBinding

class SearchAdapter(
    private val context: Context
): ListAdapter<SearchResponse, SearchAdapter.SearchViewHolder>(SearchDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder
       = SearchViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(getItem(position))

    inner class SearchViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: SearchResponse){
            binding.name.text = item.name
            binding.description.text = item.description
            binding.cost.text = item.cost.toString()
            Glide.with(context)
                .load(item.image)
                .placeholder(R.drawable.placeholder)
                .into(binding.image)

        }
    }
    object SearchDiff: DiffUtil.ItemCallback<SearchResponse>() {
        override fun areItemsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem == newItem
        }
    }

}