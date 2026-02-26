package uz.gita.maxwayappclone.presentation.screens.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.domain.usecase.OrdersUseCase

class OrdersViewModel(private val useCase: OrdersUseCase): ViewModel() {
	private val _loadingLiveData = MutableLiveData<Boolean>()
	val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData
	private val _errorMessageLiveData = MutableLiveData<String>()
	val errorMessageLiveData: LiveData<String> get() = _errorMessageLiveData
	private val _ordersLiveData = MutableLiveData<List<OrdersUIData>>()
	val orderLiveData: LiveData<List<OrdersUIData>> get() = _ordersLiveData
	private val _currentBtnLiveData = MutableLiveData<Boolean>()
	val currentBtnLiveData: LiveData<Boolean> get() = _currentBtnLiveData
	private val _historyBtnLiveData = MutableLiveData<Boolean>(false)
	val historyBtnLiveData: LiveData<Boolean> get() = _historyBtnLiveData
	private val _toMainScreenLiveData = MutableLiveData<Boolean>()
	val toMainScreenLiveData: LiveData<Boolean> get() = _toMainScreenLiveData
	private val _onClickOrder = MutableLiveData<OrdersUIData>()
	val onClickOrder: LiveData<OrdersUIData> get() = _onClickOrder
	private val _refreshLiveData = MutableLiveData<Boolean>()
	val refreshLiveData: LiveData<Boolean> get() = _refreshLiveData

	fun loadOrders() {
		useCase()
			.onStart { _loadingLiveData.value = true }
			.onCompletion { _loadingLiveData.value = false }
			.onEach { result ->
				result.onSuccess { _ordersLiveData.value = it }
				result.onFailure { _errorMessageLiveData.value = it.message ?: "Unknown exception" }
			}
			.launchIn(viewModelScope)
	}

	fun setOnClickCurrent() {
		if (_currentBtnLiveData.value == false) {
			_currentBtnLiveData.value = true
			_historyBtnLiveData.value = false
		}
	}

	fun setOnClickHistory() {
		if (_historyBtnLiveData.value == false) {
			_historyBtnLiveData.value = true
			_currentBtnLiveData.value = false
		}
	}

	fun onClickMakeOrder() {
		_toMainScreenLiveData.value = true
	}

	fun onClickOrder(data: OrdersUIData) {
		_onClickOrder.value = data
	}

	fun refresh() {
		_refreshLiveData.value = true
	}
}