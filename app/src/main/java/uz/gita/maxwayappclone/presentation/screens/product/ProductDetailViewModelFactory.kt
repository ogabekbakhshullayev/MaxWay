package uz.gita.maxwayappclone.presentation.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.ProductRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetProductCountUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.SetProductCountUseCaseImpl

class ProductDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ProductRepositoryImpl.getInstance()
        return ProductDetailViewModel(
            GetProductCountUseCaseImpl(repository),
            SetProductCountUseCaseImpl(repository)
        ) as T
    }
}
