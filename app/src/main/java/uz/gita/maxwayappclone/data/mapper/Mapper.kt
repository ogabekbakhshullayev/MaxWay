package uz.gita.maxwayappclone.data.mapper

import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.data.source.remote.response.OrderResponse

fun List<OrderResponse>.toData() = this.map {OrdersUIData(
	id = it.id,
	ls = it.ls,
	address = it.address,
	createTime = it.createTime,
	sum = it.sum
)}