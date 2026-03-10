package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.request.OrderRequest

interface MakeOrdersUseCase {
    operator fun invoke(token: String, data: OrderRequest): Flow<Result<Unit>>

}