package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.StoryApi
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.domain.repository.AuthRepository
import uz.gita.maxwayappclone.domain.repository.StoryRepository

class StoryRepositoryImpl(
    private val storyApi: StoryApi,
    private val gson: Gson
) : StoryRepository{

    companion object {
        private lateinit var instance: StoryRepository

        fun init() {
            if (!(::instance.isInitialized))
                instance = StoryRepositoryImpl(ApiClient.storyApi, Gson())
        }

        fun getInstance(): StoryRepository = instance
    }

    override suspend fun getStories(): Result<Array<StoryData>> {
        val response = storyApi.getStories()
        return (if (response.isSuccessful && response.body() != null){
            Result.success(response.body()?.data)
        }else{
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Error"))
            else{
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }) as Result<Array<StoryData>>
    }
}