package uz.gita.maxwayappclone.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.SearchRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.SearchUseCaseImpl

class SearchViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModelImpl(SearchUseCaseImpl(SearchRepositoryImpl.getInstance())) as T
    }
}