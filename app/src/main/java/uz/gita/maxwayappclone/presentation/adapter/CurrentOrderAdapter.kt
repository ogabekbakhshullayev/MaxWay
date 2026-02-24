package uz.gita.maxwayappclone.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.databinding.ItemOrderStatusBinding

class CurrentOrderAdapter: ListAdapter<OrdersUIData, CurrentOrderAdapter.ViewHolder>(diff) {
	private var listener: ((OrdersUIData) -> Unit)? = null

	fun onClick(l: ((OrdersUIData) -> Unit)) {
		listener = l
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder(ItemOrderStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

	inner class ViewHolder(private val binding: ItemOrderStatusBinding): RecyclerView.ViewHolder(binding.root) {
		init {
			binding.item.setOnClickListener { listener?.invoke(getItem(absoluteAdapterPosition)) }
		}

		fun bind() {
			val data = getItem(absoluteAdapterPosition)
			binding.orderStatusNum.text = "${data.id + 100}"
			val time = System.currentTimeMillis() - data.createTime
//			5-10
			if (time in 300001..<1200000) {
				binding.status2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
				binding.status2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.maxway_purple))
				binding.statusLine2.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.maxway_purple))
			}
//			10-15
			if (time in 600001..<1200000) {
				binding.status3.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
				binding.status3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.maxway_purple))
				binding.statusLine3.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.maxway_purple))
			}
//			15-20
			if (time in 900001..<1200000) {
				binding.status4.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
				binding.status4.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.maxway_purple))
			}
		}
	}

	companion object {
		val diff = object : DiffUtil.ItemCallback<OrdersUIData>() {
			override fun areItemsTheSame(oldItem: OrdersUIData, newItem: OrdersUIData) =
				oldItem == newItem

			override fun areContentsTheSame(oldItem: OrdersUIData, newItem: OrdersUIData) =
				oldItem == newItem
		}
	}
}