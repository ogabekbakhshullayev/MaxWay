package uz.gita.maxwayappclone.data.source.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse

interface SearchApi {

    @GET("/products_by_query")
    suspend fun search(@Query("query")  query: String): Response<GeneralResponse<List<SearchResponse>>>
}