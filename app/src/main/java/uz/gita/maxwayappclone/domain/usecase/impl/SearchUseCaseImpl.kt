package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.response.SearchResponse
import uz.gita.maxwayappclone.domain.repository.SearchRepository
import uz.gita.maxwayappclone.domain.usecase.SearchUseCase

class SearchUseCaseImpl(private val repository: SearchRepository): SearchUseCase {
    override fun invoke(query: String): Flow<Result<List<SearchResponse>>> = flow {
        emit(repository.search(query))
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}