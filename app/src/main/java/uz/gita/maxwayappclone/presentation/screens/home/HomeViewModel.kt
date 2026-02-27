package uz.gita.maxwayappclone.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.model.Category
import uz.gita.maxwayappclone.domain.model.Product
import uz.gita.maxwayappclone.domain.usecase.GetAdsUseCase
import uz.gita.maxwayappclone.domain.usecase.GetCategoriesUseCase
import uz.gita.maxwayappclone.domain.usecase.GetProductsUseCase
import uz.gita.maxwayappclone.domain.usecase.ObserveProductCountsUseCase
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase
import uz.gita.maxwayappclone.domain.usecase.StoriesUseCase
import uz.gita.maxwayappclone.data.source.remote.response.StoryData

class HomeViewModel(
    private val getAdsUseCase: GetAdsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val storiesUseCase: StoriesUseCase,
    private val observeProductCountsUseCase: ObserveProductCountsUseCase,
    private val setProductCountUseCase: SetProductCountUseCase
) : ViewModel() {

    private val _adsLiveData = MutableLiveData<List<Ad>>()
    val adsLiveData: LiveData<List<Ad>> = _adsLiveData

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> = _categoriesLiveData

    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    private val _storiesLiveData = MutableLiveData<Array<StoryData>>()
    val storiesLiveData: LiveData<Array<StoryData>> = _storiesLiveData

    private val _productCountsLiveData = MutableLiveData<Map<Long, Int>>()
    val productCountsLiveData: LiveData<Map<Long, Int>> = _productCountsLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> = _errorMessageLiveData

    init {
        observeProductCountsUseCase()
            .onEach { _productCountsLiveData.value = it }
            .launchIn(viewModelScope)
    }

    fun setProductCount(productId: Long, count: Int) {
        setProductCountUseCase(productId, count)
    }

    fun loadHome() {
        _loadingLiveData.value = true
        var pending = 4
        fun done() {
            pending -= 1
            if (pending <= 0) _loadingLiveData.value = false
        }

        getAdsUseCase()
            .onEach { result ->
                result.onSuccess { _adsLiveData.value = it }
                result.onFailure { _errorMessageLiveData.value = it.message ?: "Unknown exception" }
            }
            .onCompletion { done() }
            .launchIn(viewModelScope)

        getCategoriesUseCase()
            .onEach { result ->
                result.onSuccess { _categoriesLiveData.value = it }
                result.onFailure { _errorMessageLiveData.value = it.message ?: "Unknown exception" }
            }
            .onCompletion { done() }
            .launchIn(viewModelScope)

        getProductsUseCase()
            .onEach { result ->
                result.onSuccess { _productsLiveData.value = it }
                result.onFailure { _errorMessageLiveData.value = it.message ?: "Unknown exception" }
            }
            .onCompletion { done() }
            .launchIn(viewModelScope)

        storiesUseCase()
            .onEach { result ->
                result.onSuccess { _storiesLiveData.value = it }
                result.onFailure { _errorMessageLiveData.value = it.message ?: "Unknown exception" }
            }
            .onCompletion { done() }
            .launchIn(viewModelScope)
    }
}
