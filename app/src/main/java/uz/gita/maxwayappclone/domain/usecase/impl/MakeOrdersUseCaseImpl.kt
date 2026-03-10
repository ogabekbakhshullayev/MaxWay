package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.request.OrderRequest
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.MakeOrdersUseCase

class MakeOrdersUseCaseImpl(private val repository: ProductRepository): MakeOrdersUseCase {
    override fun invoke(token: String, data: OrderRequest): Flow<Result<Unit>> = flow {
        emit(repository.makeOrder(token,data))
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)


}