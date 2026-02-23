package uz.gita.maxwayappclone.data.source.remote.api

import androidx.compose.remote.creation.compose.state.toString
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.maxwayappclone.data.source.remote.request.SearchRequest
import uz.gita.maxwayappclone.data.source.remote.response.GeneralResponse
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse
import kotlin.toString

interface SearchApi {

    @GET("/products_by_query")
    suspend fun search(@Query("query")  query: String): Response<GeneralResponse<List<SearchResponse>>>
}