package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse

@Deprecated("")
interface SearchRepository {

    suspend fun search(query: String): Result<List<SearchResponse>>

}