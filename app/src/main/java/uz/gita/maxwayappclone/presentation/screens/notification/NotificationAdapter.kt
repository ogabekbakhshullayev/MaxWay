package uz.gita.maxwayappclone.presentation.screens.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.databinding.ItemNotificationBinding

class NotificationAdapter(
    private val context: Context,
    private val onItemClick: ((NotificationResponse)-> Unit)? = null
): ListAdapter<NotificationResponse, NotificationAdapter.NotificationViewHolder>(NotificationDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder = NotificationViewHolder(
        ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick

    )

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) = holder.bind(getItem(position))


    object NotificationDiff: DiffUtil.ItemCallback<NotificationResponse>(){
        override fun areItemsTheSame(oldItem: NotificationResponse, newItem: NotificationResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationResponse, newItem: NotificationResponse): Boolean {
            return oldItem == newItem
        }
    }


    inner class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        private val onItemClick: ((NotificationResponse) -> Unit)?
        ): RecyclerView.ViewHolder(binding.root){

        fun bind(item : NotificationResponse){
            binding.notificationName.text = item.name
            binding.notificationMassage.text = item.message
            Glide.with(context)
                .load(item.imgURL)
                .placeholder(R.drawable.placeholder)
                .into(binding.detailImage)




            binding.root.setOnClickListener { onItemClick?.invoke(item) }
        }

    }

}