package uz.gita.maxwayappclone.presentation.screens.registerPhone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.AuthRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.RegisterUseCaseImpl

class RegisterPhoneViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterPhoneViewModelImpl(RegisterUseCaseImpl(AuthRepositoryImpl.getInstance())) as T
    }
}