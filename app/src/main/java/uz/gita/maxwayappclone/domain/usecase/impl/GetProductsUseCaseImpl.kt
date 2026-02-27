package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.domain.model.Product
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.GetProductsUseCase

class GetProductsUseCaseImpl(
    private val repository: ProductRepository
) : GetProductsUseCase {
    override fun invoke(): Flow<Result<List<Product>>> = flow {
        emit(repository.getProducts())
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}
