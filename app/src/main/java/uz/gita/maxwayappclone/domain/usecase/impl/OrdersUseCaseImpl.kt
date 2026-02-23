package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.domain.repository.OrdersRepository
import uz.gita.maxwayappclone.domain.usecase.OrdersUseCase

class OrdersUseCaseImpl(private val repository: OrdersRepository): OrdersUseCase {
	override fun invoke(): Flow<Result<List<OrdersUIData>>> = flow {
		emit(repository.getOrders())
	}
		.catch { emit(Result.failure(it)) }
		.flowOn(Dispatchers.IO)
}