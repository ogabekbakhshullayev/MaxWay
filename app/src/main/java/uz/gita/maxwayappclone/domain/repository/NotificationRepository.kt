package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface NotificationRepository {

    suspend fun getAllNotifications(): Result<List<NotificationResponse>>

}