package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import uz.gita.maxwayappclone.data.source.remote.request.EditProfileRequest
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse

interface EditeProfileApi {

    @GET("/user_info")
    suspend fun getProfileInfo(@Header("token") token: String): Response<GeneralResponse<EditProfileResponse>>

    @PUT("/update_user_info")
    suspend fun updateProfileInfo(@Body data: EditProfileRequest): Response<GeneralResponse<Unit>>


}