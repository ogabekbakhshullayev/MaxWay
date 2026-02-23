package uz.gita.maxwayappclone.presentation.screens.orders

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenCurrentOrderBinding
import uz.gita.maxwayappclone.presentation.adapter.OrderItemAdapter
import uz.gita.maxwayappclone.presentation.util.getCurrent
import kotlin.getValue

class CurrentOrderFragment: Fragment(R.layout.screen_current_order) {
	private val viewModel by viewModels<OrdersViewModel>(ownerProducer = { requireParentFragment() })
	private var _binding: ScreenCurrentOrderBinding? = null
	private val binding get() = _binding!!
	private val adapter = OrderItemAdapter()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = ScreenCurrentOrderBinding.bind(view)

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

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
		viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
			Timber.tag("TTT").d("error: $message")
		}
		viewModel.orderLiveData.observe(viewLifecycleOwner) { list ->
			if (list.isNullOrEmpty()) {
				binding.emptyList.isVisible = true
				binding.recyclerView.isVisible = false
			} else {
				val newList = list.getCurrent()
				if (newList.isEmpty()) {
					binding.emptyList.isVisible = true
					binding.recyclerView.isVisible = false
				} else {
					binding.emptyList.isVisible = false
					binding.recyclerView.isVisible = true
					adapter.submitList(newList)
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}