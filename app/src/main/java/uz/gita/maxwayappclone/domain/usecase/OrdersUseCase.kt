package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.model.OrdersUIData

interface OrdersUseCase {
	operator fun invoke(): Flow<Result<List<OrdersUIData>>>
}