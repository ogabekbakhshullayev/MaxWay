package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.maxwayappclone.data.source.remote.request.RegisterRequest
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.RegisterResponse

interface AuthApi {

    @POST("/register")
    suspend fun register(@Body data: RegisterRequest): Response<GeneralResponse<RegisterResponse>>
}

// register, account crud, verify