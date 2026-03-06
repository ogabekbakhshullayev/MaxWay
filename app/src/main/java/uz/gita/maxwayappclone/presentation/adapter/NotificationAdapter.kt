package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.databinding.ItemNotificationBinding

class NotificationAdapter: ListAdapter<NotificationResponse, NotificationAdapter.NotificationViewHolder>(NotificationDiff) {

    private var onItemClickListener: ((NotificationResponse) -> Unit)? = null

    object NotificationDiff : DiffUtil.ItemCallback<NotificationResponse>() {
        override fun areItemsTheSame(oldItem: NotificationResponse, newItem: NotificationResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationResponse, newItem: NotificationResponse): Boolean {
            return oldItem == newItem
        }
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onItemClickListener?.invoke(getItem(absoluteAdapterPosition)) }
        }

        fun bind(item: NotificationResponse) {
            binding.notificationName.text = item.name
            binding.notificationMassage.text = item.message

            Glide.with(binding.root.context)
                .load(item.imgURL)
                .placeholder(R.drawable.img_placeholder)
                .into(binding.detailImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder = NotificationViewHolder(
        ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) = holder.bind(getItem(position))

    fun setOnItemClickListener(block: (NotificationResponse) -> Unit) {
        onItemClickListener = block
    }
}


