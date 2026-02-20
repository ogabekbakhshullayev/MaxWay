package uz.gita.maxwayappclone.domain.repository

interface AuthRepository {

    /**
     *  bu method user register qilish uchun
     */
    suspend fun register(phone: String) : Result<String>
    suspend fun verify(phone:String,code:Int) : Result<String>
    suspend fun repeat(phone:String): Result<String>
    suspend fun nameDate(token:String,name:String,date:String): Result<String>
}

