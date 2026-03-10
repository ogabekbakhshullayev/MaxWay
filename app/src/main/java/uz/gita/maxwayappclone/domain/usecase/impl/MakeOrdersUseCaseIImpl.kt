package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.maxwayappclone.data.source.remote.request.OrderRequest
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.MakeOrdersUseCase

class MakeOrdersUseCaseIImpl(private val repository: ProductRepository): MakeOrdersUseCase {
    override fun invoke(token: String, data: OrderRequest): Flow<Result<Unit>> = flow {
        emit(repository)
    }


}