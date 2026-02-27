package uz.gita.maxwayappclone.domain.usecase.impl

import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.SetProductCountUseCase

class SetProductCountUseCaseImpl(
    private val repository: ProductRepository
) : SetProductCountUseCase {
    override fun invoke(productId: Long, count: Int) {
        repository.setProductCount(productId, count)
    }
}
