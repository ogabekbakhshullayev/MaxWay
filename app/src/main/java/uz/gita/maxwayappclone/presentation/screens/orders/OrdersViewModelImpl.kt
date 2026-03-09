package uz.gita.maxwayappclone.presentation.screens.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.domain.usecase.OrdersUseCase

class OrdersViewModelImpl(private val useCase: OrdersUseCase): ViewModel(), OrdersViewModel {
	override val loadingLiveData = MutableLiveData<Boolean>()
	override val errorMessageLiveData = MutableLiveData<String>()
	override val orderLiveData = MutableLiveData<List<OrdersUIData>>()
	override val currentBtnLiveData = MutableLiveData<Boolean>(true)
	override val historyBtnLiveData = MutableLiveData<Boolean>(false)
	override val toMainScreenLiveData = MutableLiveData<Boolean>()
	override val onClickOrder = MutableLiveData<OrdersUIData>()
	override val refreshLiveData = MutableLiveData<Boolean>()

	override fun loadOrders() {
		useCase()
			.onStart { loadingLiveData.value = true }
			.onCompletion { loadingLiveData.value = false }
			.onEach { result ->
				result.onSuccess { orderLiveData.value = it }
				result.onFailure { errorMessageLiveData.value = it.message ?: "Unknown exception" }
			}
			.launchIn(viewModelScope)
	}

	override fun setOnClickCurrent() {
		if (currentBtnLiveData.value == false) {
			currentBtnLiveData.value = true
			historyBtnLiveData.value = false
		}
	}

	override fun setOnClickHistory() {
		if (historyBtnLiveData.value == false) {
			historyBtnLiveData.value = true
			currentBtnLiveData.value = false
		}
	}

	override fun onClickMakeOrder() {
		toMainScreenLiveData.value = true
	}

	override fun onClickOrder(data: OrdersUIData) {
		onClickOrder.value = data
	}

	override fun refresh() {
		refreshLiveData.value = true
	}

	override fun clearClickOrder() {
		onClickOrder.value = null
	}
}