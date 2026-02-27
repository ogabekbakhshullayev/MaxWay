package uz.gita.maxwayappclone.presentation.screens.productInfo

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse

interface ProductInfoViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<ProductByCategoryResponse>
    val errorMessageLiveData: LiveData<String>
    val countLiveData: LiveData<Int>
    fun increase()
    fun decrease()
    fun applyCount()
    fun bind(productId:Long)
}