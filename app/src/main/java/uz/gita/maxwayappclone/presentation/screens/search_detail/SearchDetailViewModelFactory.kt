package uz.gita.maxwayappclone.presentation.screens.search_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.ProductRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.SearchDetailUseCaseImpl

@Suppress("UNCHECKED_CAST")
class SearchDetailViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchDetailViewModelImpl(SearchDetailUseCaseImpl(ProductRepositoryImpl.getInstance())) as T
    }
}