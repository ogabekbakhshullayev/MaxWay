package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.OrderResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse

interface ProductApi {

	@GET("/my_orders")
	suspend fun getOrders(@Header("token") token: String): Response<GeneralResponse<List<OrderResponse>>>

	@GET("/products_by_category")
	suspend fun productsByCategory(): Response<GeneralResponse<Array<ProductResponse>>>
}

// search, product, category, ads, recomen, basket, history