package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import uz.gita.maxwayappclone.data.source.remote.request.NameDateRequest
import uz.gita.maxwayappclone.data.source.remote.request.RegisterRequest
import uz.gita.maxwayappclone.data.source.remote.request.RepeatRequest
import uz.gita.maxwayappclone.data.source.remote.request.VerifyRequest
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.JustMessageResponse
import uz.gita.maxwayappclone.data.source.remote.response.RegisterResponse
import uz.gita.maxwayappclone.data.source.remote.response.RepeatResponse
import uz.gita.maxwayappclone.data.source.remote.response.VerifyResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(@Body data: RegisterRequest): Response<GeneralResponse<RegisterResponse>>

    @POST("/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<GeneralResponse<VerifyResponse>>

    @POST("/repeat")
    suspend fun repeat(@Body data: RepeatRequest): Response<GeneralResponse<RepeatResponse>>

    @PUT("/update_user_info")
    suspend fun updateUserNameDate(@Header("token") token:String,@Body data: NameDateRequest): Response<JustMessageResponse>
}

// register, account crud, verify