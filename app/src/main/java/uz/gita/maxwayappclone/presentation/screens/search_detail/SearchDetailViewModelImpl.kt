package uz.gita.maxwayappclone.presentation.screens.search_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.usecase.SearchDetailUseCase

class SearchDetailViewModelImpl(private val searchItemUseCase: SearchDetailUseCase): ViewModel(), SearchDetailViewModel{
    override val productLiveData = MutableLiveData<ProductUIData?>()


    override fun getItem(id: Long) {
        val item = searchItemUseCase(id)
        productLiveData.value = item
    }
}