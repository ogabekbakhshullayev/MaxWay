package uz.gita.maxwayappclone.presentation.screens.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.domain.usecase.NotificationUseCase

class NotificationViewModelImpl(private val notificationUseCase: NotificationUseCase): ViewModel(), NotificationViewModel {

    override val loadingLiveData = MutableLiveData<Boolean>()
    override val notificationListLiveData = MutableLiveData< List<NotificationResponse>>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun getNotificationsList() {
        notificationUseCase()
            .onStart { loadingLiveData.value = true }
            .onCompletion { loadingLiveData.value = false }
            .onEach { result ->
                result.onSuccess {
                    notificationListLiveData.value = it
                }
                result.onFailure {
                    errorMessageLiveData.value = it.message?: "Unknown exception"
                }
            }
            .launchIn(viewModelScope)
    }
}