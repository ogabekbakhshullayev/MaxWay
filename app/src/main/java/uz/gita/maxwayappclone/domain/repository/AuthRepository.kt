package uz.gita.maxwayappclone.domain.repository

interface AuthRepository {

    /**
     *  bu method user register qilish uchun
     */
    suspend fun register(phone: String) : Result<String>
}

