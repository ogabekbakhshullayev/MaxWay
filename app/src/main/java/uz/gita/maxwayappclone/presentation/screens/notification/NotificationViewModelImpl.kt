package uz.gita.maxwayappclone.presentation.screens.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.domain.usecase.RegisterUseCase

class NotificationViewModelImpl(private val registerUseCase: RegisterUseCase): ViewModel(), NotificationViewModel {
    override val notificationListLiveData = MutableLiveData<NotificationResponse>()

    override fun getNotificationsList() {
        TODO("Not yet implemented")
    }
}