package uz.gita.maxwayappclone.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.ProductRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetAdsUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetCategoriesUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetProductsUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.ObserveProductCountsUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.SetProductCountUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.StoriesUseCaseImpl
import uz.gita.maxwayappclone.data.repository_impl.StoryRepositoryImpl

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ProductRepositoryImpl.getInstance()
        return HomeViewModel(
            GetAdsUseCaseImpl(repository),
            GetCategoriesUseCaseImpl(repository),
            GetProductsUseCaseImpl(repository),
            StoriesUseCaseImpl(StoryRepositoryImpl.getInstance()),
            ObserveProductCountsUseCaseImpl(repository),
            SetProductCountUseCaseImpl(repository)
        ) as T
    }
}
