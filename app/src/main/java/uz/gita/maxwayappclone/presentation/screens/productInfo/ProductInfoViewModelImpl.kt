package uz.gita.maxwayappclone.presentation.screens.productInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.domain.usecase.GetProductCountUseCase
import uz.gita.maxwayappclone.domain.usecase.ProductInfoUseCase
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase

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

    override fun bind(productId: Long) {
        if (currentProductId.toLong() == productId) return
        currentProductId = productId.toInt()
        val count = getProductCountUseCase(productId)
        countLiveData.value = if (count > 0) count else 1
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