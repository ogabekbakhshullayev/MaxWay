package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse

interface ProductInfoUseCase {
    operator fun invoke(): Flow<Result<Array<ProductResponse>>>
}