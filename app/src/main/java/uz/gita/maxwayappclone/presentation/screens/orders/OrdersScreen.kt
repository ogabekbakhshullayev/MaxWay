package uz.gita.maxwayappclone.presentation.screens.orders

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenOrdersBinding
import uz.gita.maxwayappclone.presentation.screens.main.MainScreen

class OrdersScreen: Fragment(R.layout.screen_orders) {
	private val viewModel: OrdersViewModel by viewModels<OrdersViewModelImpl>(ownerProducer = { this }) { OrdersViewModelFactory() }
	private var _binding: ScreenOrdersBinding? = null
	private val binding get() = _binding!!

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = ScreenOrdersBinding.bind(view)

		childFragmentManager.beginTransaction()
			.replace(R.id.listOrders, CurrentOrderFragment())
			.commit()

		setAction()
		observes()
		viewModel.loadOrders()
	}

	fun observes() {
		viewModel.currentBtnLiveData.observe(viewLifecycleOwner)  { bool ->
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

		viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
			Timber.tag("TTT").d("error: $message")

//			findNavController().navigate(R.id.noConnectionScreen)
		}
		viewModel.onClickOrder.observe(viewLifecycleOwner) { data ->
			if (data != null) {
				val bundle = Bundle()
				val gson = Gson()
				val json = gson.toJson(data)
				bundle.putString("DATA", json)
				findNavController().navigate(R.id.action_mainScreen_to_orderPage, bundle)
				viewModel.clearClickOrder()
			}
		}
		viewModel.refreshLiveData.observe(viewLifecycleOwner) {
			viewModel.loadOrders()
		}
		viewModel.loadingLiveData.observe(viewLifecycleOwner) { bool ->
			if (bool) binding.swipeRefresh.isRefreshing = false
		}
		viewModel.toMainScreenLiveData.observe(viewLifecycleOwner) {
			(parentFragment as MainScreen).binding.viewPager.currentItem = 0
		}
	}

	fun setAction() {
		binding.historyOrders.setOnClickListener { viewModel.setOnClickHistory() }
		binding.currentOrders.setOnClickListener { viewModel.setOnClickCurrent() }
		binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}