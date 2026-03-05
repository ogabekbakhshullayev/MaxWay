package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.model.ProductUIData

interface GetProductsUseCase {
    operator fun invoke(): Flow<Result<List<ProductUIData>>>
}
