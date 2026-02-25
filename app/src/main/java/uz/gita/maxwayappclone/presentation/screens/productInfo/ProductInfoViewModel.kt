package uz.gita.maxwayappclone.presentation.screens.productInfo

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse

interface ProductInfoViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<ProductResponse>
    val errorMessageLiveData: LiveData<String>

    fun productByCategory(cId:Int)
}