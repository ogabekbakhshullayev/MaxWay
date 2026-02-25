package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.maxwayappclone.domain.model.Product
import uz.gita.maxwayappclone.domain.usecase.ClearBasketUseCase
import uz.gita.maxwayappclone.domain.usecase.GetProductsUseCase
import uz.gita.maxwayappclone.domain.usecase.ObserveProductCountsUseCase
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase

class BasketViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val observeProductCountsUseCase: ObserveProductCountsUseCase,
    private val setProductCountUseCase: SetProductCountUseCase,
    private val clearBasketUseCase: ClearBasketUseCase
) : ViewModel() {

    private val _basketItemsLiveData = MutableLiveData<List<BasketItem>>()
    val basketItemsLiveData: LiveData<List<BasketItem>> = _basketItemsLiveData

    private val _totalLiveData = MutableLiveData<Long>()
    val totalLiveData: LiveData<Long> = _totalLiveData

    private val _emptyLiveData = MutableLiveData<Boolean>()
    val emptyLiveData: LiveData<Boolean> = _emptyLiveData

    private var products: List<Product> = emptyList()
    private var counts: Map<Long, Int> = emptyMap()

    init {
        observeProductCountsUseCase()
            .onEach {
                counts = it
                updateBasket()
            }
            .launchIn(viewModelScope)
    }

    fun load() {
        getProductsUseCase()
            .onEach { result ->
                result.onSuccess {
                    products = it
                    updateBasket()
                }
            }
            .launchIn(viewModelScope)
    }

    fun setProductCount(productId: Long, count: Int) {
        setProductCountUseCase(productId, count)
    }

    fun clearBasket() {
        clearBasketUseCase()
    }

    private fun updateBasket() {
        val isEmpty = counts.isEmpty()
        if (isEmpty) {
            _basketItemsLiveData.value = emptyList()
            _totalLiveData.value = 0L
            _emptyLiveData.value = true
            return
        }

        val items = products.mapNotNull { product ->
            val count = counts[product.id] ?: 0
            if (count > 0) BasketItem(product, count) else null
        }
        _basketItemsLiveData.value = items
        _totalLiveData.value = items.sumOf { it.product.cost * it.count }
        _emptyLiveData.value = false
    }
}
