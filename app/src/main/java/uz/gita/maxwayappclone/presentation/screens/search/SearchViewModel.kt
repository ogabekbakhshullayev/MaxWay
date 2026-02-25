package uz.gita.maxwayappclone.presentation.screens.search

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse

interface SearchViewModel {
    val loadingLiveData: LiveData<Boolean>
    val searchLiveData: LiveData<List<SearchResponse>>
    val errorMessageLiveData: LiveData<String>

    fun getSearchResult(query: String)
}