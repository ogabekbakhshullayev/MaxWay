package uz.gita.maxwayappclone.data.source.remote.response

data class OrderResponse(
	val id: Long,
	val userID: Long,
	val ls: List<OrderData>,
	val latitude: Double,
	val longitude: Double,
	val address: String,
	val createTime: Long,
	val sum: Long
)

data class OrderData (
	val count: Long,
	val productData: ProductData
)

data class ProductData(
	val id: Long,
	val categoryID: Long,
	val name: String,
	val description: String,
	val image: String,
	val cost: Long
)