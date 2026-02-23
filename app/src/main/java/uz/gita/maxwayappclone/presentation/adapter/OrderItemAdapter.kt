package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.databinding.ItemOrderBinding
import uz.gita.maxwayappclone.presentation.util.getDate
import uz.gita.maxwayappclone.presentation.util.toFormatted

class OrderItemAdapter: ListAdapter<OrdersUIData, OrderItemAdapter.OrderViewHolder>(diff) {
	private var listener: ((OrdersUIData) -> Unit)? = null

	fun onClick(l: ((OrdersUIData) -> Unit)) {
		listener = l
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

	override fun onBindViewHolder(holder: OrderViewHolder, position: Int) = holder.bind()

	inner class OrderViewHolder(private val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root) {
		init {
			binding.item.setOnClickListener { listener?.invoke(getItem(absoluteAdapterPosition)) }
		}

		fun bind() {
			val data = getItem(absoluteAdapterPosition)
			binding.number.text = "№${data.id + 100}"
			binding.date.text = data.createTime.getDate()
			binding.price.text = "${data.sum.toFormatted()} sum"
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