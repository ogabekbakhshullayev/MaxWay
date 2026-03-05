package uz.gita.maxwayappclone.presentation.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.usecase.SearchUseCase

class SearchViewModelImpl(private val searchUseCase: SearchUseCase) : ViewModel(), SearchViewModel {
    override val productsLiveData = MutableLiveData<List<ProductUIData>>()
    override val emptyResultLiveData = MutableLiveData<Boolean>()

    override fun getSearchResult(query: String) {
        if (query == "") {
            productsLiveData.value = emptyList()
        } else {
            val ls = searchUseCase(query)
            emptyResultLiveData.value = ls.isEmpty()
            productsLiveData.value = ls
        }
    }
}
