package uz.gita.maxwayappclone.presentation.screens.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.maxwayappclone.domain.usecase.GetProductCountUseCase
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase

class ProductDetailViewModel(
    private val getProductCountUseCase: GetProductCountUseCase,
    private val setProductCountUseCase: SetProductCountUseCase
) : ViewModel() {

    private val _qtyLiveData = MutableLiveData<Int>()
    val qtyLiveData: LiveData<Int> = _qtyLiveData

    private var currentProductId: Long = -1L

    fun bind(productId: Long) {
        if (currentProductId == productId) return
        currentProductId = productId
        val count = getProductCountUseCase(productId)
        _qtyLiveData.value = if (count > 0) count else 1
    }

    fun increase() {
        val current = _qtyLiveData.value ?: 1
        val next = current + 1
        _qtyLiveData.value = next
    }

    fun decrease() {
        val current = _qtyLiveData.value ?: 1
        if (current <= 1) return
        val next = current - 1
        _qtyLiveData.value = next
    }

    fun applyCurrentCount() {
        val current = _qtyLiveData.value ?: 1
        if (currentProductId != -1L) {
            setProductCountUseCase(currentProductId, current)
        }
    }
}
