package uz.gita.maxwayappclone.presentation.screens.search

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.model.ProductUIData

interface SearchViewModel {
    val productsLiveData: LiveData<List<ProductUIData>>
    val emptyResultLiveData: LiveData<Boolean>

    fun getSearchResult(query: String)
}