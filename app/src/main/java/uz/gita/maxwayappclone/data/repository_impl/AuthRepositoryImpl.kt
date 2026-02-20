package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.AuthApi
import uz.gita.maxwayappclone.data.source.remote.request.NameDateRequest
import uz.gita.maxwayappclone.data.source.remote.request.RegisterRequest
import uz.gita.maxwayappclone.data.source.remote.request.RepeatRequest
import uz.gita.maxwayappclone.data.source.remote.request.VerifyRequest
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.data.tokenManager.TokenManager
import uz.gita.maxwayappclone.domain.repository.AuthRepository

class AuthRepositoryImpl private constructor(
    private val authApi: AuthApi,
    private val gson: Gson
) : AuthRepository {

    companion object {
        private lateinit var instance: AuthRepository

        fun init() {
            if (!(::instance.isInitialized))
                instance = AuthRepositoryImpl(ApiClient.authApi, Gson())
        }

        fun getInstance(): AuthRepository = instance
    }


    override suspend fun register(phone: String): Result<String> {
        val request = RegisterRequest(phone)
        val response = authApi.register(request)
        return if (response.isSuccessful && response.body() != null)
            Result.success(response.body()?.data?.code.toString())
        else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }

    override suspend fun verify(phone: String, code: Int): Result<String> {
        val request = VerifyRequest(phone, code)
        val response = authApi.verify(request)
        return if (response.isSuccessful && response.body() != null) {
            val token = response.body()?.data?.token.toString()
            TokenManager().putToken(token)
            Result.success(token)
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Error"))
            else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }

    override suspend fun repeat(phone: String): Result<String> {
        val request = RepeatRequest(phone)
        val response = authApi.repeat(request)
        return if (response.isSuccessful && response.body() != null)
            Result.success(response.body()?.data?.code.toString())
        else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Error"))
            else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }

    override suspend fun nameDate(token: String, name: String, date: String): Result<String> {
        val request = NameDateRequest(name, date)
        val response = authApi.updateUserNameDate(TokenManager().getToken(), request)
        return if (response.isSuccessful && response.body() != null)
            Result.success(response.body()?.message.toString())
        else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Error"))
            else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }
}