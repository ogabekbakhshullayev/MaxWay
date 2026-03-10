package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.maxwayappclone.data.source.remote.request.OrderRequest
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse

interface OrderApi {
    @POST("/create_order")
    suspend fun order(@Header("token") token: String, @Body data: OrderRequest): Response<GeneralResponse<Unit>>
}