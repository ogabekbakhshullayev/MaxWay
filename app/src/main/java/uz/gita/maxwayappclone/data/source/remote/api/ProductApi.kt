package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.OrderResponse
import uz.gita.maxwayappclone.data.source.remote.response.AdResponse
import uz.gita.maxwayappclone.data.source.remote.response.CategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse

interface ProductApi {

	@GET("/my_orders")
	suspend fun getOrders(@Header("token") token: String): Response<GeneralResponse<List<OrderResponse>>>

    @GET("/ads")
    suspend fun getAds(): Response<GeneralResponse<List<AdResponse>>>

    @GET("/categories")
    suspend fun getCategories(): Response<GeneralResponse<List<CategoryResponse>>>

    @GET("/products")
    suspend fun getProducts(): Response<GeneralResponse<List<ProductResponse>>>
}

// search, product, category, ads, recomen, basket, history
