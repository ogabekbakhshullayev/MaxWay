package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.mapper.toProductList
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.ProductApi
import uz.gita.maxwayappclone.data.source.remote.request.RecommendedRequest
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.domain.repository.RecommendRepository

class RecommendRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val gson: Gson
): RecommendRepository {
    override suspend fun getRecommendedProducts(ids: RecommendedRequest): Result<List<ProductUIData>> {
        val response = productApi.postRecommendedProducts(ids)
        return if (response.isSuccessful && response.body() != null) {
            val products = response.body()!!.data.toProductList()
            Result.success(products)
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) {
                Result.failure(Throwable("Unknown exception"))
            } else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }

    companion object {
        private lateinit var instance: RecommendRepository

        fun getInstance(): RecommendRepository {
            if (!::instance.isInitialized)
                instance = RecommendRepositoryImpl(ApiClient.productApi, Gson())

            return instance
        }
    }
}