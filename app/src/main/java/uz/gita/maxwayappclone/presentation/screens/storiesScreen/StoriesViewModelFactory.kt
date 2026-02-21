package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.StoryRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.StoriesUseCaseImpl

class StoriesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StoriesViewModelImpl(StoriesUseCaseImpl(StoryRepositoryImpl.getInstance())) as T
    }
}