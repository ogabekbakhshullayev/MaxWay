package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.ObserveProductCountsUseCase

class ObserveProductCountsUseCaseImpl(
    private val repository: ProductRepository
) : ObserveProductCountsUseCase {
    override fun invoke(): Flow<Map<Long, Int>> {
        return repository.observeProductCounts()
    }
}
