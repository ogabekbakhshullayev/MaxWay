package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.ProductRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.ClearBasketUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetProductsUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.ObserveProductCountsUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.SetProductCountUseCaseImpl

class BasketViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ProductRepositoryImpl.getInstance()
        return BasketViewModel(
            GetProductsUseCaseImpl(repository),
            ObserveProductCountsUseCaseImpl(repository),
            SetProductCountUseCaseImpl(repository),
            ClearBasketUseCaseImpl(repository)
        ) as T
    }
}
