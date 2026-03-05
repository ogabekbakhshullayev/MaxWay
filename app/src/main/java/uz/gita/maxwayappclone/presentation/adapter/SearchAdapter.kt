package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.databinding.ItemHomeProductBinding
import uz.gita.maxwayappclone.utils.formatPrice
import uz.gita.maxwayappclone.utils.loadImageWithGlide

class SearchAdapter(): ListAdapter<ProductUIData, SearchAdapter.SearchViewHolder>(SearchDiffUtil) {

    object SearchDiffUtil: DiffUtil.ItemCallback<ProductUIData>() {
        override fun areItemsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUIData, newItem: ProductUIData): Boolean {
            return oldItem == newItem
        }
    }

    inner class SearchViewHolder(private val binding: ItemHomeProductBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnAdd.setOnClickListener {
                getItem(absoluteAdapterPosition).count ++
                notifyItemChanged(absoluteAdapterPosition)
            }

            binding.btnMinus.setOnClickListener {
                getItem(absoluteAdapterPosition).count --
                notifyItemChanged(absoluteAdapterPosition)
            }

            binding.btnPlus.setOnClickListener {
                getItem(absoluteAdapterPosition).count ++
                notifyItemChanged(absoluteAdapterPosition)
            }
        }

        fun bind(){
            getItem(absoluteAdapterPosition).apply {
                binding.tvName.text = this.name
                binding.tvDesc.text = this.description
                binding.tvPrice.text = this.cost.formatPrice()
                binding.ivProduct.loadImageWithGlide(this.image)
                binding.tvQty.text = this.count.toString()

                binding.btnAdd.isVisible = this.count == 0
                binding.countContainer.isVisible = this.count != 0
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder
            = SearchViewHolder(ItemHomeProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind()

}