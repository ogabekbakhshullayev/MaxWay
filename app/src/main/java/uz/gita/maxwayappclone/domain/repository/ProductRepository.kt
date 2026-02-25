package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse

interface ProductRepository {
    suspend fun productByCategory(): Result<Array<ProductResponse>>
}