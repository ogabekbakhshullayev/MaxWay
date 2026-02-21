package uz.gita.maxwayappclone.presentation.screens.notification


import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface NotificationViewModel {

    val notificationListLiveData: LiveData<NotificationResponse>

    fun getNotificationsList()



}