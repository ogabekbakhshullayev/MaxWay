package uz.gita.maxwayappclone.presentation.screens.prfileDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.EditeProfileRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.EditProfileDialogUseCaseImp

class EditProfileViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditProfileViewModelImpl(EditProfileDialogUseCaseImp(EditeProfileRepositoryImpl.Companion.getInstance())) as T
    }
}