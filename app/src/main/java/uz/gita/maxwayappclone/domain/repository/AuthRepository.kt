package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface AuthRepository {

    /**
     *  bu method user register qilish uchun
     */
    suspend fun register(phone: String) : Result<Unit>
}

