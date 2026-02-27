package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.domain.model.Product

interface GetProductsUseCase {
    operator fun invoke(): Flow<Result<List<Product>>>
}
