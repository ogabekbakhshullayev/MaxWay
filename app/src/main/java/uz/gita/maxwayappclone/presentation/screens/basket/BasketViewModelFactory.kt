package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.ProductRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetProductsInBasketUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.MakeOrdersUseCaseImpl

class BasketViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ProductRepositoryImpl.getInstance()
        return BasketViewModelImpl(
            GetProductsInBasketUseCaseImpl(repository),
            MakeOrdersUseCaseImpl(repository)
        ) as T
    }
}
