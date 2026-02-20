package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.gita.maxwayappclone.data.model.BranchResponseDto

interface BranchApi {

    @GET("/branches")
    suspend fun getBranches(): Response<BranchResponseDto>
}

// filiallar
