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
import uz.gita.maxwayappclone.MainActivity
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenOrdersBinding
import uz.gita.maxwayappclone.presentation.screens.main.MainScreen

class OrdersScreen: Fragment(R.layout.screen_orders) {
	val viewModel by viewModels<OrdersViewModel>(ownerProducer = { this }) { OrdersViewModelFactory() }
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
			(parentFragment as MainScreen).navigateToPage(0)
		}
		viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
			Timber.tag("TTT").d("error: $message")

			parentFragmentManager.setFragmentResultListener("key", viewLifecycleOwner) { _, bundle ->
				val result = bundle.getBoolean("refresh")
				if (result) viewModel.refresh()
			}

			parentFragmentManager.beginTransaction()
				.replace(R.id.main, NoConnectionScreen())
				.addToBackStack(null)
				.commit()
		}
		viewModel.onClickOrder.observe(viewLifecycleOwner) { data ->
			val bundle = Bundle()
			val gson = Gson()
			val json = gson.toJson(data)
			bundle.putString("DATA", json)
			val fragment = OrderPage()
			fragment.arguments = bundle

			parentFragmentManager.beginTransaction()
				.replace(R.id.main, fragment)
				.addToBackStack(null)
				.commit()
		}
		viewModel.refreshLiveData.observe(viewLifecycleOwner) {
			viewModel.loadOrders()
		}
		viewModel.loadingLiveData.observe(viewLifecycleOwner) { bool ->
			if (bool) binding.swipeRefresh.isRefreshing = false
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