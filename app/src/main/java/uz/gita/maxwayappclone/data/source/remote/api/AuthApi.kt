package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.maxwayappclone.data.source.remote.request.RegisterRequest
import uz.gita.maxwayappclone.data.source.remote.request.VerifyRequest
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.RegisterResponse
import uz.gita.maxwayappclone.data.source.remote.response.VerifyResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(@Body data: RegisterRequest): Response<GeneralResponse<RegisterResponse>>

    @POST("/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<GeneralResponse<VerifyResponse>>
}

// register, account crud, verify