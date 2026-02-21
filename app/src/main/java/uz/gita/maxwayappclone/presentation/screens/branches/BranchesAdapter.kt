package uz.gita.maxwayappclone.presentation.screens.branches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.databinding.ItemBranchBinding
import uz.gita.maxwayappclone.domain.model.Branch

class BranchesAdapter(
    private val onItemClick: ((Branch) -> Unit)? = null
) : ListAdapter<Branch, BranchesAdapter.BranchViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val binding = ItemBranchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BranchViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BranchViewHolder(
        private val binding: ItemBranchBinding,
        private val onItemClick: ((Branch) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Branch) {
            binding.tvName.text = item.name
            binding.tvAddress.text = item.address
            val time = "${item.openTime}-${item.closeTime}"
            binding.tvWorkValue.text = time
            binding.tvDeliveryValue.text = time
            binding.tvPhoneValue.text = item.phone

            binding.root.setOnClickListener { onItemClick?.invoke(item) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Branch>() {
        override fun areItemsTheSame(oldItem: Branch, newItem: Branch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Branch, newItem: Branch): Boolean {
            return oldItem == newItem
        }
    }
}
