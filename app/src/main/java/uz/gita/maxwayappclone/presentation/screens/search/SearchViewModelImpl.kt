package uz.gita.maxwayappclone.presentation.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse
import uz.gita.maxwayappclone.domain.usecase.SearchUseCase

class SearchViewModelImpl(private val searchUseCase: SearchUseCase): ViewModel(), SearchViewModel {
    override val loadingLiveData = MutableLiveData<Boolean>()
    override val searchLiveData = MutableLiveData<List<SearchResponse>>()
    override val errorMessageLiveData = MutableLiveData<String>()



        private var getSearchResultJob: Job? = null
    override fun getSearchResult(query: String) {
        getSearchResultJob?.cancel()
        getSearchResultJob = viewModelScope.launch {
            delay(700)
            searchUseCase(query)
                .onStart { loadingLiveData.value = true }
                .onCompletion { loadingLiveData.value = false }
                .onEach { result ->
                    result.onSuccess { searchLiveData.value = it }
                    result.onFailure { errorMessageLiveData.value = it.message?: "Unknown exception" }
                }
                .collect()

        }

    }
}