package uz.gita.maxwayappclone.presentation.screens.productInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.ProductRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.ProductInfoUseCaseImpl

class ProductInfoViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductInfoViewModelImpl(ProductInfoUseCaseImpl(ProductRepositoryImpl.getInstance())) as T
    }

}