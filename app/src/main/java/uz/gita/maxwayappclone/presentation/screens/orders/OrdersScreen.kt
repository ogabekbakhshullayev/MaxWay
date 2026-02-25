package uz.gita.maxwayappclone.presentation.screens.orders

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import uz.gita.maxwayappclone.MainActivity
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenOrdersBinding
import uz.gita.maxwayappclone.presentation.adapter.CategoryOrderAdapter
import uz.gita.maxwayappclone.presentation.screens.home.HomeScreen

class OrdersScreen: Fragment(R.layout.screen_orders) {
	private val viewModel by viewModels<OrdersViewModel>(ownerProducer = { this }) { OrdersViewModelFactory() }
	private var _binding: ScreenOrdersBinding? = null
	private val binding get() = _binding!!

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = ScreenOrdersBinding.bind(view)

		childFragmentManager.beginTransaction()
			.replace(R.id.listOrders, CurrentOrderFragment())
			.commit()

		setAction()
		observe()
		viewModel.loadOrders()
	}

	fun observe() {
		viewModel.currentBtnLiveData.observe(viewLifecycleOwner) { bool ->
			if (bool) {
				binding.currentOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
				childFragmentManager.beginTransaction()
					.replace(R.id.listOrders, CurrentOrderFragment())
					.commit()
			} else {
				binding.currentOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.none))
			}
		}
		viewModel.historyBtnLiveData.observe(viewLifecycleOwner) { bool ->
			if (bool) {
				binding.historyOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
				childFragmentManager.beginTransaction()
					.replace(R.id.listOrders, HistoryOrderFragment())
					.commit()
			} else {
				binding.historyOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.none))
			}
		}
		viewModel.toMainScreenLiveData.observe(viewLifecycleOwner){
//			(activity as MainActivity).binding.viewPager.currentItem = 0
		}
		viewModel.onClickOrder.observe(viewLifecycleOwner) { data ->
			parentFragmentManager.beginTransaction()
				.replace(R.id.main, HomeScreen())
				.addToBackStack(null)
				.commit()
		}
	}

	fun setAction() {
		binding.historyOrders.setOnClickListener { viewModel.setOnClickHistory() }
		binding.currentOrders.setOnClickListener { viewModel.setOnClickCurrent() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}