package uz.gita.maxwayappclone.presentation.screens.search_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase

class SearchDetailViewModelImpl(private val setProductCountUseCase:  SetProductCountUseCase): ViewModel(), SearchDetailViewModel{

    override val productLiveData = MutableLiveData<ProductUIData?>()
    override var countLiveData = MutableLiveData<Int>(1)
    override val totalPriceLiveData = MutableLiveData<Long>()
    private var productPrice: Long = 0

    init {
        calculateTotal()
    }

    override fun increase() {
        val current = countLiveData.value ?: 1
        val next = current + 1
        countLiveData.value = next
        calculateTotal()

    }

    override fun decrease() {
        val current = countLiveData.value ?: 1
        if (current <= 1) return
        val next = current - 1
        countLiveData.value = next
        calculateTotal()
        }

    override fun saveCount(productId: Long) {
        val current = countLiveData.value ?: 1
        if (current != -1){
            setProductCountUseCase(productId,current)
        }
    }

    override fun setPrice(price: Long){
        productPrice = price
        calculateTotal()
    }

    private fun calculateTotal(){
        val current = countLiveData.value ?: 1
        totalPriceLiveData.value = current * productPrice

    }

}