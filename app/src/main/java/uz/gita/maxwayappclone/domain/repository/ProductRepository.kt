package uz.gita.maxwayappclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.model.Category

interface ProductRepository {
    suspend fun getAds(): Result<List<Ad>>
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getProducts(): Result<List<ProductUIData>>
    fun search(query: String): List<ProductUIData>
    fun getProductsInBasket(): List<ProductUIData>

    fun observeProductCounts(): Flow<Map<Long, Int>>
    fun getProductCount(productId: Long): Int
    fun setProductCount(productId: Long, count: Int)
    fun clearProductCounts()
    suspend fun productByCategory(): Result<Array<ProductByCategoryResponse>>
}