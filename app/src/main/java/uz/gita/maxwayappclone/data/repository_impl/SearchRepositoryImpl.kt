package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.SearchApi
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse
import uz.gita.maxwayappclone.domain.repository.AuthRepository
import uz.gita.maxwayappclone.domain.repository.SearchRepository

class SearchRepositoryImpl private constructor(
    private val searchApi: SearchApi,
    private val gson: Gson
): SearchRepository {


    companion object{

        private lateinit var instance: SearchRepository


        fun init(){
            if (!(::instance.isInitialized))
                instance = SearchRepositoryImpl(ApiClient.searchApi, Gson())
        }
        fun getInstance(): SearchRepository = instance
    }


    override suspend fun search(query: String): Result<List<SearchResponse>> {
        val response = searchApi.search(query)

        return if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.data)
        }
        else{
            val errorJson = response.errorBody()?.toString()
            if (errorJson.isNullOrEmpty()){
                Result.failure(Throwable("Unknown exception"))
            }
            else{
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }

    }
}