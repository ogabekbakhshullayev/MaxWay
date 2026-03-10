package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.remote.request.RecommendedRequest
import uz.gita.maxwayappclone.domain.repository.RecommendRepository
import uz.gita.maxwayappclone.domain.usecase.RecommendUseCase

class RecommendUseCaseImpl(private val repository: RecommendRepository): RecommendUseCase {
    override fun invoke(ids: RecommendedRequest): Flow<Result<List<ProductUIData>>> = flow {
        emit(repository.getRecommendedProducts(ids))
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}