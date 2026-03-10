package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.model.OrderProduct
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.data.source.remote.request.OrderRequest
import uz.gita.maxwayappclone.data.source.remote.request.RecommendedRequest
import uz.gita.maxwayappclone.domain.usecase.GetProductsInBasketUseCase
import uz.gita.maxwayappclone.domain.usecase.MakeOrdersUseCase
import uz.gita.maxwayappclone.domain.usecase.RecommendUseCase

class BasketViewModelImpl(
    private val getProductsInBasketUseCase: GetProductsInBasketUseCase,
    private val getRecommendUseCase: RecommendUseCase,
    private val makeOrdersUseCase: MakeOrdersUseCase
) : ViewModel(), BasketViewModel {

    override val basketItemsLiveData = MutableLiveData<List<ProductUIData>>()
    override val recommendItemsLiveData = MutableLiveData<List<ProductUIData>>()
    override val totalLiveData = MutableLiveData<Long>()
    override val emptyLiveData = MutableLiveData<Boolean>()
    override val loadingLiveData = MutableLiveData<Boolean>()
    override val orderSuccessLiveData =  MutableLiveData<String>()
    private val orderProduct = ArrayList<OrderProduct>()
    override val orderErrorMassageLiveData = MutableLiveData<String>()

    init {
        load()
        getRecommend()
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
        load()
    }

    override fun getRecommend() {
        if (basketItemsLiveData.value == null) return
        getRecommendUseCase(RecommendedRequest(
            basketItemsLiveData.value!!.map { it.id }
        ))
            .onEach { result ->
                result.onSuccess { recommendItemsLiveData.value = it }
            }
            .launchIn(viewModelScope)
    }

    override fun makeOrder() {
        val orderList = basketItemsLiveData.value?.map {
            OrderProduct(
                productID = it.id,
                count = it.count

            )
        }
        makeOrdersUseCase(TokenManager.token, OrderRequest(orderList,"41.00","69.00","TEST"))
            .onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }
            .onEach {result->
                result.onSuccess {
                    orderSuccessLiveData.value = it.toString()
                }
                result.onFailure {
                    orderErrorMassageLiveData.value = it.message?: "Unknown exception"
                }

            }
            .launchIn(viewModelScope)

    }
}


