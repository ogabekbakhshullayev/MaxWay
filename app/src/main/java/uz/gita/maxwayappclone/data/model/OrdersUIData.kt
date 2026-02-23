package uz.gita.maxwayappclone.data.model

import uz.gita.maxwayappclone.data.source.remote.response.OrderData

data class OrdersUIData (
	val id: Long,
	val ls: List<OrderData>,
	val address: String,
	val createTime: Long,
	val sum: Long
)

