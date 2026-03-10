package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.maxwayappclone.data.source.remote.request.RecommendedRequest
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.OrderResponse
import uz.gita.maxwayappclone.data.source.remote.response.AdResponse
import uz.gita.maxwayappclone.data.source.remote.response.CategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse

interface ProductApi {

    @POST("/recommended_products")
    suspend fun postRecommendedProducts(@Body data: RecommendedRequest): Response<GeneralResponse<List<ProductResponse>>>

	@GET("/my_orders")
	suspend fun getOrders(@Header("token") token: String): Response<GeneralResponse<List<OrderResponse>>>

	@GET("/products_by_category")
	suspend fun productsByCategory(): Response<GeneralResponse<Array<ProductByCategoryResponse>>>

    @GET("/ads")
    suspend fun getAds(): Response<GeneralResponse<List<AdResponse>>>

    @GET("/categories")
    suspend fun getCategories(): Response<GeneralResponse<List<CategoryResponse>>>

    @GET("/products")
    suspend fun getProducts(): Response<GeneralResponse<List<ProductResponse>>>
}

// search, product, category, ads, recommend, basket, history