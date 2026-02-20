package uz.gita.maxwayappclone.presentation.screens.branches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.BranchRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.GetBranchesUseCaseImpl

class BranchesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BranchesViewModel(
            GetBranchesUseCaseImpl(BranchRepositoryImpl.getInstance())
        ) as T
    }
}
