package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.remote.request.RecommendedRequest

interface RecommendRepository {
    suspend fun getRecommendedProducts(ids: RecommendedRequest): Result<List<ProductUIData>>
}