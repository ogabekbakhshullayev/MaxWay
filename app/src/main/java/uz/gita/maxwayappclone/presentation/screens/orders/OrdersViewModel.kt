package uz.gita.maxwayappclone.presentation.screens.orders

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.model.OrdersUIData

interface OrdersViewModel {
    val loadingLiveData: LiveData<Boolean>
    val errorMessageLiveData: LiveData<String>
    val orderLiveData: LiveData<List<OrdersUIData>>
    val currentBtnLiveData: LiveData<Boolean>
    val historyBtnLiveData: LiveData<Boolean>
    val toMainScreenLiveData: LiveData<Boolean>
    val onClickOrder: LiveData<OrdersUIData>
    val refreshLiveData: LiveData<Boolean>

    fun loadOrders()
    fun setOnClickCurrent()
    fun setOnClickHistory()
    fun onClickMakeOrder()
    fun onClickOrder(data: OrdersUIData)
    fun refresh()
    fun clearClickOrder()
}