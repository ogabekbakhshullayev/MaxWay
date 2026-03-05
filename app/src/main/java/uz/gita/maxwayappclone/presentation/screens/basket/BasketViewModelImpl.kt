package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.usecase.ClearBasketUseCase
import uz.gita.maxwayappclone.domain.usecase.GetProductsInBasketUseCase
import uz.gita.maxwayappclone.domain.usecase.GetProductsUseCase
import uz.gita.maxwayappclone.domain.usecase.ObserveProductCountsUseCase
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase

class BasketViewModelImpl(
    private val getProductsInBasketUseCase: GetProductsInBasketUseCase
) : ViewModel(), BasketViewModel {

    override val basketItemsLiveData = MutableLiveData<List<ProductUIData>>()
    override val totalLiveData = MutableLiveData<Long>()
    override val emptyLiveData = MutableLiveData<Boolean>()

    init {
        load()
    }
    
    override fun load() {
        val ls = getProductsInBasketUseCase.invoke()
        emptyLiveData.value = ls.isEmpty()
        basketItemsLiveData.value = ls
        totalLiveData.value = ls.sumOf { it.cost * it.count }
    }

    override fun clearBasket() {
        getProductsInBasketUseCase.invoke()
            .forEach { it.count = 0 }
    }
}


