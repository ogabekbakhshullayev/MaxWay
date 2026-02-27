package uz.gita.maxwayappclone.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.model.Category
import uz.gita.maxwayappclone.domain.model.Product

interface ProductRepository {
    suspend fun getAds(): Result<List<Ad>>
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getProducts(): Result<List<Product>>
    fun observeProductCounts(): Flow<Map<Long, Int>>
    fun getProductCount(productId: Long): Int
    fun setProductCount(productId: Long, count: Int)
    fun clearProductCounts()
}
