package uz.gita.maxwayappclone.presentation.screens.notification


import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface NotificationViewModel {
    val loadingLiveData: LiveData<Boolean>
    val notificationListLiveData: LiveData<NotificationResponse>
    val errorMessageLiveData: LiveData<String>

    fun getNotificationsList()



}