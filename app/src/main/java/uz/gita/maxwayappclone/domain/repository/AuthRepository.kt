package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface AuthRepository {

    /**
     *  bu method user register qilish uchun
     */
    suspend fun register(phone: String) : Result<String>
    suspend fun verify(phone:String,code:Int) : Result<String>
    suspend fun repeat(phone:String): Result<String>
    suspend fun nameDate(token:String,name:String,date:String): Result<String>
}

