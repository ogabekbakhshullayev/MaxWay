package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.ProductInfoUseCase

class ProductInfoUseCaseImpl(private val repository: ProductRepository) : ProductInfoUseCase {
    override fun invoke(): Flow<Result<Array<ProductByCategoryResponse>>> = flow {
        emit(repository.productByCategory())
    }.catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}