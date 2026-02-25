package uz.gita.maxwayappclone.presentation.screens.productInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.data.util.checkConnection
import uz.gita.maxwayappclone.domain.usecase.NameDateUseCase
import uz.gita.maxwayappclone.domain.usecase.ProductInfoUseCase
import uz.gita.maxwayappclone.presentation.screens.registerName.RegisterNameViewModel
import uz.gita.maxwayappclone.presentation.screens.registerVerify.RegisterVerifyViewModel

class ProductInfoViewModelImpl(private val productInfoUseCase: ProductInfoUseCase) :
    ProductInfoViewModel,
    ViewModel() {

    override val loadingLiveData = MutableLiveData<Boolean>()
    override val successLiveData = MutableLiveData<ProductResponse>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun productByCategory(cId: Int) {
        productInfoUseCase().onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }
            .onEach {
                it.onSuccess {
                    successLiveData.value = it[cId-1]
                }.onFailure {
                    errorMessageLiveData.value = it.message ?: "Error"
                }
            }.launchIn(viewModelScope)
    }


}