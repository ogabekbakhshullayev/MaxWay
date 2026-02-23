package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.StoryData

interface StoryApi {
    @GET("/stories")
    suspend fun getStories(): Response<GeneralResponse<Array<StoryData>>>
}