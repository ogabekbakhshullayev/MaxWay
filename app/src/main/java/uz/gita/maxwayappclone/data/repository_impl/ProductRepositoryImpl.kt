package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import uz.gita.maxwayappclone.data.mapper.toAdList
import uz.gita.maxwayappclone.data.mapper.toCategoryList
import uz.gita.maxwayappclone.data.mapper.toProductList
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.AuthApi
import uz.gita.maxwayappclone.data.source.remote.api.ProductApi
import uz.gita.maxwayappclone.data.source.remote.request.RegisterRequest
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.model.Category
import uz.gita.maxwayappclone.domain.model.Product
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.domain.repository.AuthRepository
import uz.gita.maxwayappclone.domain.repository.ProductRepository

class ProductRepositoryImpl private constructor(
    private val productApi: ProductApi,
    private val gson: Gson
) : ProductRepository {

    private val productCounts = MutableStateFlow<Map<Long, Int>>(emptyMap())

    companion object {
        private lateinit var instance: ProductRepository

        fun init() {
            if (!::instance.isInitialized) {
                instance = ProductRepositoryImpl(ApiClient.productApi, Gson())
            }
        }

        fun getInstance(): ProductRepository = instance
    }

    override suspend fun getAds(): Result<List<Ad>> {
        val response = productApi.getAds()
        return if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.data.toAdList())
        } else {
            Result.failure(parseError(response.errorBody()?.string()))
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        val response = productApi.getCategories()
        return if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.data.toCategoryList())
        } else {
            Result.failure(parseError(response.errorBody()?.string()))
        }
    }

    override suspend fun getProducts(): Result<List<Product>> {
        val response = productApi.getProducts()
        return if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.data.toProductList())
        } else {
            Result.failure(parseError(response.errorBody()?.string()))
        }
    }

    override fun observeProductCounts(): Flow<Map<Long, Int>> = productCounts

    override fun getProductCount(productId: Long): Int {
        return productCounts.value[productId] ?: 0
    }

    override fun setProductCount(productId: Long, count: Int) {
        productCounts.update { current ->
            val next = current.toMutableMap()
            if (count <= 0) {
                next.remove(productId)
            } else {
                next[productId] = count
            }
            next.toMap()
        }
    }

    override fun clearProductCounts() {
        productCounts.value = emptyMap()
    }

    override suspend fun productByCategory(): Result<Array<ProductByCategoryResponse>> {
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
        }) as Result<Array<ProductByCategoryResponse>>
    }

    private fun parseError(errorJson: String?): Throwable {
        if (errorJson.isNullOrEmpty()) return Throwable("Unknown exception")
        val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
        return Throwable(errorMessage.message)
    }
}


