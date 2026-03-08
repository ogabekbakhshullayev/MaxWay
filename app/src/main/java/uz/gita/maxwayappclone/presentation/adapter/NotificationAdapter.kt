package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.databinding.ItemNotificationBinding
import uz.gita.maxwayappclone.utils.loadImageWithGlide

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

        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.notificationName.text = this.name
                binding.notificationMassage.text = this.message
                binding.detailImage.loadImageWithGlide(this.imgURL)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder = NotificationViewHolder(
        ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) = holder.bind()

    fun setOnItemClickListener(block: (NotificationResponse) -> Unit) {
        onItemClickListener = block
    }
}


