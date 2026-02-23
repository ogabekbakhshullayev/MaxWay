package uz.gita.maxwayappclone.presentation.screens.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.gita.maxwayappclone.data.repository_impl.NotificationRepositoryImpl
import uz.gita.maxwayappclone.domain.usecase.impl.NotificationUseCaseImpl

class NotificationViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModelImpl(NotificationUseCaseImpl(NotificationRepositoryImpl.getInstance())) as T
    }
}