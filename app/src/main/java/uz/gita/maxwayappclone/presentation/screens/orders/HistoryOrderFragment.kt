package uz.gita.maxwayappclone.presentation.screens.orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenHistroyOrderBinding
import uz.gita.maxwayappclone.presentation.adapter.OrderItemAdapter
import kotlin.getValue

class HistoryOrderFragment: Fragment(R.layout.screen_histroy_order) {
	private val viewModel by viewModels<OrdersViewModel>(ownerProducer = { requireParentFragment() })
	private var _binding: ScreenHistroyOrderBinding? = null
	private val binding get() = _binding!!
	private val adapter = OrderItemAdapter()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = ScreenHistroyOrderBinding.bind(view)

		binding.recyclerView.adapter = adapter
		val layoutManager = LinearLayoutManager(requireContext())
		layoutManager.reverseLayout = true
		layoutManager.stackFromEnd = true
		binding.recyclerView.layoutManager = layoutManager

		observe()
		setAction()
	}

	fun setAction() {
		binding.btnAddOrder.setOnClickListener {
			viewModel.onClickMakeOrder()
		}
		adapter.onClick { data -> viewModel.onClickOrder(data) }
	}

	fun observe() {
		viewModel.loadingLiveData.observe(viewLifecycleOwner) { bool ->
			binding.loadingProgress.isVisible = bool
		}
		viewModel.orderLiveData.observe(viewLifecycleOwner) { list ->
			if (list.isNullOrEmpty()) {
				binding.emptyList.isVisible = true
				binding.recyclerView.isVisible = false
			} else {
				binding.emptyList.isVisible = false
				binding.recyclerView.isVisible = true
				adapter.submitList(list)
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}