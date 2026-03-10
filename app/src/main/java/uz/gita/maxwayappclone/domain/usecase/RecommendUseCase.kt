package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.remote.request.RecommendedRequest

interface RecommendUseCase {
    operator fun invoke(ids: RecommendedRequest): Flow<Result<List<ProductUIData>>>
}