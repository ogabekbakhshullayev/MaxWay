package uz.gita.maxwayappclone.presentation.screens.productInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.data.util.checkConnection
import uz.gita.maxwayappclone.domain.usecase.GetProductCountUseCase
import uz.gita.maxwayappclone.domain.usecase.NameDateUseCase
import uz.gita.maxwayappclone.domain.usecase.ProductInfoUseCase
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase
import uz.gita.maxwayappclone.presentation.screens.registerName.RegisterNameViewModel
import uz.gita.maxwayappclone.presentation.screens.registerVerify.RegisterVerifyViewModel

class ProductInfoViewModelImpl(
    private val productInfoUseCase: ProductInfoUseCase,
    private val getProductCountUseCase: GetProductCountUseCase,
    private val setProductCountUseCase: SetProductCountUseCase
) :
    ProductInfoViewModel,
    ViewModel() {

    override val loadingLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<ProductByCategoryResponse>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val countLiveData = MutableLiveData<Int>()
    private var currentProductId: Int = -1

    override fun productByCategory(cId: Int, pId: Int) {
        if (currentProductId == pId) return
        currentProductId = pId
        val count = getProductCountUseCase(pId.toLong())
        countLiveData.value = if (count > 0) count else 1

        productInfoUseCase().onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }
            .onEach {
                it.onSuccess {
                    successLiveData.value = it[cId - 1]
                }.onFailure {
                    errorMessageLiveData.value = it.message ?: "Error"
                }
            }.launchIn(viewModelScope)
    }

    override fun increase() {
        val current = countLiveData.value ?: 1
        val next = current + 1
        countLiveData.value = next
    }

    override fun decrease() {
        val current = countLiveData.value ?: 1
        if (current <= 1) return
        val next = current - 1
        countLiveData.value = next
    }

    override fun applyCount() {
        val current = countLiveData.value ?: 1
        if (currentProductId != -1) {
            setProductCountUseCase(currentProductId.toLong(), current)
        }
    }


}