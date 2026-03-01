package uz.gita.maxwayappclone.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.EditeProfileRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.EditeProfileUseCaseImpl

class ProfileViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModelImpl(EditeProfileUseCaseImpl(EditeProfileRepositoryImpl.getInstance())) as T
    }
}