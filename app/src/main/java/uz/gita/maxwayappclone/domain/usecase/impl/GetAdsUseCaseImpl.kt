package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.domain.model.Ad
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.GetAdsUseCase

class GetAdsUseCaseImpl(
    private val repository: ProductRepository
) : GetAdsUseCase {
    override fun invoke(): Flow<Result<List<Ad>>> = flow {
        emit(repository.getAds())
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}
