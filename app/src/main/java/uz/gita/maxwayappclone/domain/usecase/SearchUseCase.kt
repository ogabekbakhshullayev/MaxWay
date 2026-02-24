package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse

interface SearchUseCase {

    operator fun invoke(query: String): Flow<Result<List<SearchResponse>>>
}