package uz.gita.maxwayappclone.presentation.screens.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.AuthRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.RegisterUseCaseImpl

class TestViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TestViewModelImpl(RegisterUseCaseImpl(AuthRepositoryImpl.getInstance())) as T
    }
}