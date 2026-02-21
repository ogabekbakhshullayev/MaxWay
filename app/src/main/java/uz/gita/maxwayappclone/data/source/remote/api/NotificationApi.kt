package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface NotificationApi {
	
	
	@GET("/notifications")
	suspend fun notification(): Response<GeneralResponse<NotificationResponse>>



}