package uz.gita.maxwayappclone.presentation.screens.registerVerify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.AuthRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.RepeatUseCaseImpl
import uz.gita.maxwayappclone.domain.usecase.impl.VerifyUseCaseImpl

class RegisterVerifyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterVerifyViewModelImpl(
            VerifyUseCaseImpl(AuthRepositoryImpl.getInstance()),
            RepeatUseCaseImpl(AuthRepositoryImpl.getInstance())
        ) as T
    }
}