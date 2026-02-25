package uz.gita.maxwayappclone.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.EditeProfileRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.EditeProfileUseCase

class ProfileViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModelImpl(EditeProfileRepositoryImpl.getInstance() as EditeProfileUseCase) as T
    }
}