package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.AuthApi
import uz.gita.maxwayappclone.data.source.remote.api.ProductApi
import uz.gita.maxwayappclone.data.source.remote.request.RegisterRequest
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.domain.repository.AuthRepository
import uz.gita.maxwayappclone.domain.repository.ProductRepository

class ProductRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val gson: Gson
) : ProductRepository {

    companion object {
        private lateinit var instance: ProductRepository

        fun init() {
            if (!(::instance.isInitialized))
                instance = ProductRepositoryImpl(ApiClient.productApi, Gson())
        }

        fun getInstance(): ProductRepository = instance
    }

    override suspend fun productByCategory(): Result<Array<ProductResponse>> {
        val response = productApi.productsByCategory()
        return (if (response.isSuccessful && response.body() != null)
            Result.success(response.body()?.data)
        else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Unknown exception"))
            else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }) as Result<Array<ProductResponse>>
    }
}