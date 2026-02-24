package uz.gita.maxwayappclone.presentation.screens.orders

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import uz.gita.maxwayappclone.R
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.databinding.ItemOrderPriceBinding
import uz.gita.maxwayappclone.databinding.PageOrderBinding
import uz.gita.maxwayappclone.presentation.util.getDate
import uz.gita.maxwayappclone.presentation.util.toFormatted

class OrderPage: Fragment(R.layout.page_order) {
	private var _binding: PageOrderBinding? = null
	private val binding get() = _binding!!
	private lateinit var data: OrdersUIData

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = PageOrderBinding.bind(view)

		val json = arguments?.getString("DATA")
		data = Gson().fromJson(json, OrdersUIData::class.java)

		val time = System.currentTimeMillis() - data.createTime
		if (time < 1200000) {
			binding.title.text = "Current Order"
			binding.btnReorder.isVisible = false
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
		} else {
			binding.title.text = "History"
			binding.status.isVisible = false
		}
		binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
		binding.numberOrder.text = "Order №${data.id + 100}"
		binding.orderStatusNum.text = "Order №${data.id + 100}"
		binding.date.text = data.createTime.getDate().substring(0, 10)
		data.ls.forEach {
			val product = layoutInflater.inflate(R.layout.item_order_price, binding.orderList, false)
			val binding = ItemOrderPriceBinding.bind(product)
			binding.title.text = "${it.productData.name} ${it.count}x"
			binding.price.text = "${it.productData.cost.toFormatted()} sum"
			this.binding.orderList.addView(product)
		}
		binding.totalAmount.text = "${data.sum.toFormatted()} sum"
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}