package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.model.OrdersUIData

interface OrdersRepository {
	suspend fun getOrders(): Result<List<OrdersUIData>>
}