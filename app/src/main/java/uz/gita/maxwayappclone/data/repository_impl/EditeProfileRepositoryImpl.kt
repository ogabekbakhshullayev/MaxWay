package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.EditeProfileApi
import uz.gita.maxwayappclone.data.source.remote.request.EditProfileRequest
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.domain.repository.EditeProfileRepository

class EditeProfileRepositoryImpl private constructor(
    private val editeProfileApi: EditeProfileApi,
    private val gson: Gson): EditeProfileRepository {


        companion object{
            private lateinit var instance: EditeProfileRepository

            fun init(){
                if (!(::instance.isInitialized)){
                    instance = EditeProfileRepositoryImpl(ApiClient.editeProfileApi,Gson())
                }
            }
            fun getInstance(): EditeProfileRepository = instance
        }

    override suspend fun getProfileInfo(token: String): Result<EditProfileResponse> {
        val response = editeProfileApi.getProfileInfo(token)
        return (if (response.isSuccessful && response.body() != null){
            Result.success(response.body()!!.data)
        } else{
            val errorJson = response.errorBody()?.toString()

            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Unknown exception"))
            else{
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        })
    }



    override suspend fun updateProfileInfo(name: String, birthDate: String): Result<String> {
        val request = EditProfileRequest(name,birthDate)
        val response = editeProfileApi.updateProfileInfo(request)

        return if (response.isSuccessful && response.body() != null){
            Result.success(response.body()?.data.toString())
        }
        else{
            val errJson = response.errorBody()?.toString()
            if (errJson.isNullOrEmpty()) Result.failure(Throwable("Unknown exception"))

            else{
                val errorMessage = gson.fromJson(errJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }

        }

    }
}


