package uz.gita.maxwayappclone.presentation.screens.orders

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.model.OrdersUIData
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
		viewModel.currentBtnLiveData.observe(viewLifecycleOwner, currentBtnLiveDataObserver)
		viewModel.historyBtnLiveData.observe(viewLifecycleOwner, historyBtnLiveDataObserver)

		viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageLiveDataObserver)
		viewModel.onClickOrder.observe(viewLifecycleOwner, onClickOrderObserver)
		viewModel.refreshLiveData.observe(viewLifecycleOwner, refreshLiveDataObserver)
		viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingLiveDataObserver)
		viewModel.toMainScreenLiveData.observe(viewLifecycleOwner, toMainScreenLiveDataObserver)
	}

	private val currentBtnLiveDataObserver = Observer<Boolean> { bool ->
		if (bool) {
			binding.currentOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
			childFragmentManager.beginTransaction()
				.replace(R.id.listOrders, CurrentOrderFragment())
				.commit()
		} else {
			binding.currentOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.none))
		}
	}

	private val historyBtnLiveDataObserver = Observer<Boolean> { bool ->
		if (bool) {
			binding.historyOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
			childFragmentManager.beginTransaction()
				.replace(R.id.listOrders, HistoryOrderFragment())
				.commit()
		} else {
			binding.historyOrders.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.none))
		}
	}

	private val errorMessageLiveDataObserver = Observer<String> { message ->
		Timber.tag("TTT").d("error: $message")

//			findNavController().navigate(R.id.noConnectionScreen)
	}

	private val onClickOrderObserver = Observer<OrdersUIData> { data ->
		if (data != null) {
			val bundle = Bundle()
			val gson = Gson()
			val json = gson.toJson(data)
			bundle.putString("DATA", json)
			findNavController().navigate(R.id.action_mainScreen_to_orderPage, bundle)
			viewModel.clearClickOrder()
		}
	}

	private val refreshLiveDataObserver = Observer<Boolean> {
		viewModel.loadOrders()
	}

	private val loadingLiveDataObserver = Observer<Boolean> { bool ->
		if (bool) binding.swipeRefresh.isRefreshing = false
	}

	private val toMainScreenLiveDataObserver = Observer<Boolean> {
		(parentFragment as MainScreen).binding.viewPager.currentItem = 0
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